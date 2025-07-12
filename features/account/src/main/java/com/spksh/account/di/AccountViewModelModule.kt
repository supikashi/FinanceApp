package com.spksh.account.di

import androidx.lifecycle.ViewModel
import com.spksh.account.ui.view_model.AccountUpdateViewModel
import com.spksh.account.ui.view_model.AccountViewModel
import com.spksh.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AccountViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountUpdateViewModel::class)
    fun bindAccountUpdateViewModel(accountUpdateViewModel: AccountUpdateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel
}