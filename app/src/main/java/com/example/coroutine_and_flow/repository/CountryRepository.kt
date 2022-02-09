package com.example.coroutine_and_flow.repository

import com.example.coroutine_and_flow.data.network.responses.Country

interface CountryRepository {

    suspend fun getAllCountries(): List<Country>
}