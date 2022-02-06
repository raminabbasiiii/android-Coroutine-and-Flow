package com.example.coroutine_and_flow.network

import com.example.coroutine_and_flow.model.CountryModel
import retrofit2.http.GET

interface ApiService {

    @GET("country")
    suspend fun getCountries(): List<CountryModel>
}