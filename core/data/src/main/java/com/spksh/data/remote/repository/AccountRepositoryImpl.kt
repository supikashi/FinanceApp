package com.spksh.data.remote.repository

import android.util.Log
import com.spksh.domain.repository.AccountRepository
import com.spksh.data.remote.api.AccountApiService
import com.spksh.data.remote.model.toUpdateDTO
import com.spksh.domain.model.Account
import com.spksh.domain.model.AccountUpdateData
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Реализация [com.spksh.domain.repository.AccountRepository], получающая данные об аккаунтах из API
 */
class AccountRepositoryImpl @Inject constructor(
    private val api: AccountApiService
) : AccountRepository {
    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    private val scope = CoroutineScope(Dispatchers.IO)
    init {
        Log.i("my_tag", "repo init")
        scope.launch {
            //delay(1000L)
            loadAccounts()
        }
    }

    override suspend fun loadAccounts() {
        try {
            val remoteAccounts = api.getAccounts()
            _accounts.value = remoteAccounts.map {it.toAccount()}
        } catch (e: Throwable) {

        }
    }

    override fun getAccountsFlow(): StateFlow<List<Account>> = _accounts

    override suspend fun updateAccount(accountId: Long, updateData: AccountUpdateData) : Account {
        val account = api.updateAccount(
            id = accountId,
            updateRequest = updateData.toUpdateDTO()
        )
        loadAccounts()
        return account.toAccount()
    }
}