package com.spksh.financeapp.di

import androidx.lifecycle.ViewModel
import com.spksh.di.ViewModelKey
import com.spksh.financeapp.ui.viewModel.NetworkViewModel


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(NetworkViewModel::class)
  fun bindNetworkViewModel(networkViewModel: NetworkViewModel): ViewModel
}
