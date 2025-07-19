package com.spksh.transactions.di

import androidx.lifecycle.ViewModel
import com.spksh.di.ViewModelKey
import com.spksh.transactions.ui.view_model.analysis.IncomeAnalysisViewModel
import com.spksh.transactions.ui.view_model.analysis.SpendingAnalysisViewModel
import com.spksh.transactions.ui.view_model.history.IncomeHistoryViewModel
import com.spksh.transactions.ui.view_model.transaction.IncomeViewModel
import com.spksh.transactions.ui.view_model.history.SpendingHistoryViewModel
import com.spksh.transactions.ui.view_model.transaction.SpendingViewModel
import com.spksh.transactions.ui.view_model.transaction.TransactionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TransactionsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SpendingViewModel::class)
    fun bindSpendingViewModel(spendingViewModel: SpendingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomeViewModel::class)
    fun bindIncomeViewModel(incomeViewModel: IncomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SpendingHistoryViewModel::class)
    fun bindSpendingHistoryViewModel(spendingHistoryViewModel: SpendingHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomeHistoryViewModel::class)
    fun bindIncomeHistoryViewModel(incomeHistoryViewModel: IncomeHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    fun bindTransactionViewModel(transactionViewModel: TransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomeAnalysisViewModel::class)
    fun bindIncomeAnalysisViewModel(incomeAnalysisViewModel: IncomeAnalysisViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SpendingAnalysisViewModel::class)
    fun bindSpendingAnalysisViewModel(spendingAnalysisViewModel: SpendingAnalysisViewModel): ViewModel
}