package com.spksh.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory @Inject constructor(
  private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    val viewModelProvider = viewModels[modelClass]
      ?: throw kotlin.IllegalArgumentException("Unknown model class $modelClass")

    try {
      return viewModelProvider.get() as T
    } catch (e: Exception) {
      throw kotlin.RuntimeException(e)
    }
  }
}