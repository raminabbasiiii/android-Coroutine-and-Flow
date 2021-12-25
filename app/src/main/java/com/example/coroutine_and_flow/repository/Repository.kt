package com.example.coroutine_and_flow.repository

import com.example.coroutine_and_flow.datasource.network.CountryModel
import com.example.coroutine_and_flow.datasource.network.MainApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository
@Inject
constructor(
    private val service: MainApiService
) {

    fun getCountries(): Flow<List<CountryModel>> {
        return flow {
            try {
                val countryList = service.getCountries()
                emit(countryList)
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }
}