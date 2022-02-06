package com.example.coroutine_and_flow.repository

import com.example.coroutine_and_flow.network.ApiService
import com.example.coroutine_and_flow.model.CountryModel
import javax.inject.Inject

class CountryRepositoryImp
@Inject
constructor(
    private val apiService: ApiService
): CountryRepository{

    override suspend fun getAllCountries(): List<CountryModel> {
        return apiService.getCountries()
    }
}