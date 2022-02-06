package com.example.coroutine_and_flow.di

import com.example.coroutine_and_flow.network.ApiService
import com.example.coroutine_and_flow.repository.CountryRepository
import com.example.coroutine_and_flow.repository.CountryRepositoryImp
import com.example.coroutine_and_flow.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCountryRepository(
        apiService: ApiService
    ) = CountryRepositoryImp(apiService) as CountryRepository

}