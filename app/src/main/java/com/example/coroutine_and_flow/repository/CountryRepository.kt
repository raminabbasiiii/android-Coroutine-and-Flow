package com.example.coroutine_and_flow.repository

import com.example.coroutine_and_flow.model.CountryModel

interface CountryRepository {

    suspend fun getAllCountries(): List<CountryModel>
}