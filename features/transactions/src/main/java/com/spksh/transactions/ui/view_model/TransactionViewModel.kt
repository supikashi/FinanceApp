package com.spksh.transactions.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spksh.domain.model.Account
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.domain.useCase.GetCategoriesByTypeUseCase
import com.spksh.domain.useCase.GetZoneIdUseCase
import com.spksh.domain.useCase.LoadAccountsUseCase
import com.spksh.transactions.domain.use_case.CreateTransactionUseCase
import com.spksh.transactions.domain.use_case.DeleteTransactionUseCase
import com.spksh.transactions.domain.use_case.GetTransactionUseCase
import com.spksh.transactions.domain.use_case.UpdateTransactionUseCase
import com.spksh.transactions.ui.model.TransactionRequestUiModel
import com.spksh.transactions.ui.state.TransactionScreenState
import com.spksh.ui.model.toUiModel
import com.spksh.ui.state.UiState
import com.spksh.ui.utils.multipleFetch
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.coroutines.cancellation.CancellationException

class TransactionViewModel @Inject constructor(
    getAccountsFlowUseCase: GetAccountsFlowUseCase,
    private val loadAccountsUseCase: LoadAccountsUseCase,
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getZoneIdUseCase: GetZoneIdUseCase,
) : ViewModel() {
    private val accountsFlow = getAccountsFlowUseCase()
    private var transactionId: Long? = null
    private var isIncome = false
    private var fetchJob: Job? = null
    private var collectJob: Job? = null
    private var job: Job? = null
    private val _uiState = MutableStateFlow<UiState<TransactionScreenState>>(UiState.Loading)
    val uiState: StateFlow<UiState<TransactionScreenState>> = _uiState
    init {
        Log.i("my_tag", "init transaction viewmodel")
    }
    fun setViewModel(id: Long?, isIncome: Boolean) {
        if (transactionId != id || collectJob == null) {
            Log.i("my_tag", "set id")
            transactionId = id
            this.isIncome = isIncome
            collectJob?.cancel()
            collectJob = viewModelScope.launch {
                accountsFlow.collect { accounts ->
                    fetchData(accounts)
                }
            }
        }
    }

    fun retryLoad() {
        viewModelScope.launch {
            loadAccountsUseCase()
            fetchData(accountsFlow.value)
        }
    }

    fun fetchData(accountsList: List<Account>) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                multipleFetch(
                    fetch = {
                        val zoneId = getZoneIdUseCase()
                        val dateFormatter = DateTimeFormatter
                            .ofPattern("dd.MM.yyyy")
                            .withZone(zoneId)
                        val categories = getCategoriesByTypeUseCase(isIncome).map {it.toUiModel()}
                        val now = LocalDateTime.now()
                        val transaction = transactionId?.let {
                            val response = getTransactionUseCase(it)
                            val dateTime = response.transactionDate.atZone(zoneId).toLocalDateTime()
                            TransactionRequestUiModel(
                                accountId = response.account.id,
                                categoryId = response.category.id,
                                amount = response.amount.toString(),
                                dateString = dateFormatter.format(response.transactionDate),
                                timeString = timeFormater(dateTime.minute + 60 * dateTime.hour),
                                date = dateTime.toLocalDate(),
                                time = dateTime.minute + 60 * dateTime.hour,
                                comment = response.comment ?: ""
                            )
                        } ?: TransactionRequestUiModel(
                            accountId = accountsFlow.value.first().id,
                            categoryId = categories.first().id,
                            dateString = dateFormatter.format(now),
                            timeString = timeFormater(now.minute + 60 * now.hour),
                            date = now.toLocalDate(),
                            time = now.minute + 60 * now.hour,
                        )

                        _uiState.value = UiState.Success(
                            TransactionScreenState(
                                transaction = transaction,
                                transactionId = transactionId,
                                categoryList = categories,
                                accountList = accountsList.map { it.toUiModel() }
                            )
                        )
                    }
                )
            } catch (e: CancellationException) {
            } catch (e: Throwable) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    fun localUpdateTransaction(transactionRequestUiModel: TransactionRequestUiModel) {
        (_uiState.value as? UiState.Success)?.let {
            _uiState.value = UiState.Success(
                it.data.copy(transaction = transactionRequestUiModel)
            )
        }
    }
    fun changeDate(newDate: Long?) {
        newDate?.let { newDate ->
            (_uiState.value as? UiState.Success)?.let {
                val zoneId = getZoneIdUseCase()
                val dateFormatter = DateTimeFormatter
                    .ofPattern("dd.MM.yyyy")
                    .withZone(zoneId)
                val date = Instant
                    .ofEpochMilli(newDate)
                    .atOffset(ZoneOffset.UTC)
                    .toLocalDate()
                _uiState.value = UiState.Success(
                    it.data.copy(transaction = it.data.transaction.copy(date = date, dateString = dateFormatter.format(date)))
                )
            }
        }
    }
    fun changeTime(newTime: Int) {
        (_uiState.value as? UiState.Success)?.let {
            _uiState.value = UiState.Success(
                it.data.copy(transaction = it.data.transaction.copy(time = newTime, timeString = timeFormater(newTime)))
            )
        }
    }

    fun updateTransaction(
        transactionRequestUiModel: TransactionRequestUiModel,
        popBackStack: () -> Unit,
        onError: (String) -> Unit,
    ) {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                multipleFetch{
                    val zoneId = getZoneIdUseCase()
                    val id = transactionId
                    if (id == null) {
                        createTransactionUseCase(transactionRequestUiModel.toTransactionRequest(zoneId))
                    } else {
                        updateTransactionUseCase(id, transactionRequestUiModel.toTransactionRequest(zoneId))
                    }
                }
                if (isActive) {
                    popBackStack()
                }
            } catch (e: CancellationException) {
            } catch (e: Throwable) {
                delay(1000L)
                onError(e.message ?: "")
            }
        }
    }

    fun deleteTransaction(
        popBackStack: () -> Unit,
        onError: (String) -> Unit,
    ) {
        transactionId?.let {
            job?.cancel()
            job = viewModelScope.launch {
                try {
                    multipleFetch {
                        deleteTransactionUseCase(it)
                    }
                    if (isActive) {
                        popBackStack()
                    }
                } catch (e: CancellationException) {
                } catch (e: Throwable) {
                    delay(1000L)
                    onError(e.message ?: "")
                }
            }
        } ?: popBackStack()
    }

    private fun timeFormater(time: Int) : String {
        val hours = time / 60
        val hoursStr = if (hours < 10) {
            "0$hours"
        } else {
            "$hours"
        }
        val minutes = time % 60
        val minutesStr = if (minutes < 10) {
            "0$minutes"
        } else {
            "$minutes"
        }
        return "$hoursStr:$minutesStr"
    }
}