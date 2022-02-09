package com.example.coroutine_and_flow.repository

import com.example.coroutine_and_flow.data.network.ApiService
import com.example.coroutine_and_flow.data.network.responses.Country
import javax.inject.Inject

class CountryRepositoryImp
@Inject
constructor(
    private val apiService: ApiService
): CountryRepository{

    override suspend fun getAllCountries(): List<Country> {
        return apiService.getCountries()
    }
}