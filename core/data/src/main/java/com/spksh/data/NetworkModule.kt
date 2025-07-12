package com.spksh.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spksh.data.remote.api.AccountApiService
import com.spksh.data.remote.api.CategoryApiService
import com.spksh.data.remote.api.TransactionApiService
import com.spksh.domain.repository.AccountRepository
import com.spksh.domain.useCase.GetAccountsFlowUseCase
import com.spksh.financeapp.data.BuildConfig
//import com.spksh.financeapp.BuildConfig
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * DI-модуль, предоставляющий сетевые зависимости
 */
@Module
object NetworkModule {

    private const val BASE_URL = "https://shmr-finance.ru/api/v1/"
    private const val AUTH_TOKEN = BuildConfig.API_KEY

    @Singleton
    @Provides
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val requestWithAuth: Request = original.newBuilder()
            .header("Authorization", "Bearer $AUTH_TOKEN")
            .build()
        chain.proceed(requestWithAuth)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    fun provideAccountApiService(retrofit: Retrofit): AccountApiService =
        retrofit.create(AccountApiService::class.java)

    @Singleton
    @Provides
    fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService =
        retrofit.create(CategoryApiService::class.java)

    @Singleton
    @Provides
    fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService =
        retrofit.create(TransactionApiService::class.java)
}