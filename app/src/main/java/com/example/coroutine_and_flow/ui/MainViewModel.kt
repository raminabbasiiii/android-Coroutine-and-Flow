package com.example.coroutine_and_flow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutine_and_flow.datasource.network.CountryModel
import com.example.coroutine_and_flow.datasource.network.MainApiService
import com.example.coroutine_and_flow.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: Repository
): ViewModel() {

    private val _countries : MutableLiveData<List<CountryModel>> = MutableLiveData()

    val countries: LiveData<List<CountryModel>>
    get() = _countries

    init {
        countryList()
    }

    private fun countryList() {
        repository
            .getCountries()
            .onEach { _countries.value = it }
            .launchIn(viewModelScope)
       /*viewModelScope.launch {
           repository
               .getCountries()
               .collect {
                   _countries.value = it
               }
       }*/
    }
}