package com.spksh.financeapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spksh.financeapp.BuildConfig
import com.spksh.financeapp.data.NetworkConnectivityObserver
import com.spksh.financeapp.data.remote.api.AccountApiService
import com.spksh.financeapp.data.remote.api.CategoryApiService
import com.spksh.financeapp.data.remote.api.TransactionApiService
import com.spksh.financeapp.domain.ConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://shmr-finance.ru/api/v1/"
    private const val AUTH_TOKEN = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val requestWithAuth: Request = original.newBuilder()
            .header("Authorization", "Bearer $AUTH_TOKEN")
            .build()
        chain.proceed(requestWithAuth)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
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

    @Provides
    @Singleton
    fun provideAccountApiService(retrofit: Retrofit): AccountApiService =
        retrofit.create(AccountApiService::class.java)

    @Provides
    @Singleton
    fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService =
        retrofit.create(CategoryApiService::class.java)

    @Provides
    @Singleton
    fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService =
        retrofit.create(TransactionApiService::class.java)
}