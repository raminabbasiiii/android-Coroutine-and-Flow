package com.example.coroutine_and_flow.data.network

import com.example.coroutine_and_flow.data.network.responses.Country
import retrofit2.http.GET

interface ApiService {

    @GET("country")
    suspend fun getCountries(): List<Country>
}