package com.spksh.data.repository

import android.util.Log
import com.spksh.data.local.dao.AccountDao
import com.spksh.data.local.model.AccountEntity
import com.spksh.data.local.model.toEntity
import com.spksh.domain.repository.AccountRepository
import com.spksh.data.remote.api.AccountApiService
import com.spksh.data.remote.model.AccountUpdateDTO
import com.spksh.data.remote.model.toUpdateDTO
import com.spksh.domain.model.Account
import com.spksh.domain.model.AccountUpdateData
import com.spksh.domain.model.Category
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

/**
 * Реализация [AccountRepository], получающая данные об аккаунтах из API
 */
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApiService,
    private val dao: AccountDao
) : AccountRepository {
    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    private val scope = CoroutineScope(Dispatchers.IO)
    init {
        Log.i("my_tag", "repo init")
        scope.launch {
            dao.getAllItemsFlow().collect {
                _accounts.value = it.map { it.toAccount() }
            }
        }
    }

    override suspend fun synchronizeDatabase() {
        val localData = dao.getAllItems()
        val remoteData = api.getAccounts()
        remoteData.forEach { remoteAccount ->
            val localAccount = localData.find { it.remoteId == remoteAccount.id }
            if (localAccount == null) {
                dao.insert(remoteAccount.toEntity())
            } else {
                val remoteTime = Instant.parse(remoteAccount.updatedAt)
                val localTime = Instant.parse(localAccount.updatedAt)
                if (localTime.isBefore(remoteTime)) {
                    dao.update(remoteAccount.toEntity().copy(localAccount.localId))
                }
            }
        }
    }

    override suspend fun synchronizeUpdated() {
        val unsynced = dao.getUnsyncedItems()
        val remoteAccounts = api.getAccounts()
        unsynced.forEach { localAccount ->
            if (localAccount.remoteId == null) {
                TODO() /// for account creation
            } else {
                val remoteAccount = remoteAccounts.find { it.id == localAccount.remoteId }
                if (remoteAccount == null) {
                    TODO() /// for account deletion
                } else {
                    val remoteTime = Instant.parse(remoteAccount.updatedAt)
                    val localTime = Instant.parse(localAccount.updatedAt)
                    if (remoteTime.isBefore(localTime)) {
                        val response = api.updateAccount(
                            id = localAccount.remoteId,
                            updateRequest = AccountUpdateDTO(
                                name = localAccount.name,
                                balance = localAccount.balance.toString(),
                                currency = localAccount.currency
                            )
                        )
                        dao.update(response.toEntity().copy(localId = localAccount.localId))
                    } else {
                        dao.update(remoteAccount.toEntity().copy(localId = localAccount.localId))
                    }
                }
            }
        }
    }

    override fun getAccountsFlow(): StateFlow<List<Account>> = _accounts

    override suspend fun updateAccount(account: Account) {
        dao.update(
            account.copy(updatedAt = Instant.now().toString()).toEntity().copy(isSync = false)
        )
        if (account.remoteId != null) {
            try {
                val remoteAccount = api.updateAccount(
                    id = account.remoteId!!,
                    updateRequest = AccountUpdateDTO(
                        name = account.name,
                        balance = account.balance.toString(),
                        currency = account.currency
                    )
                )
                dao.update(
                    remoteAccount.toEntity().copy(localId = account.localId)
                )
            } catch (e: Exception) {

            }
        }
    }

    override suspend fun loadFromNetwork(): List<Account>? {
        try {
            val local = dao.getAllItems().map {it.toAccount()}
            if (local.isEmpty()) {
                val response = api.getAccounts()
                dao.insertAll(response.map {it.toEntity()})
                Log.i("my_tag", "account load success")
                return response.map {it.toAccount()}
            } else {
                Log.i("my_tag", "local account load success")
                return local
            }
        } catch (e: Exception) {
            Log.i("my_tag", "account load error")
            return null
        }
    }
}