package com.example.coroutine_and_flow.datasource.network

import retrofit2.http.GET

interface MainApiService {

    @GET("country")
    suspend fun getCountries(): List<CountryModel>
}