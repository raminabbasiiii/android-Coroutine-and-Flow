package com.example.coroutine_and_flow.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutine_and_flow.datasource.network.CountryModel
import com.example.coroutine_and_flow.datasource.network.MainApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val service: MainApiService
): ViewModel() {

    val countries = MutableLiveData<List<CountryModel>>()

    init {
        countryList()
    }

    private fun countryList() {
        CoroutineScope(Dispatchers.IO).launch {
            service.getCountries()
                .let {
                    countries.postValue(it)
                }
        }
    }
}