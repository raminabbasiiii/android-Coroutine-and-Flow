package com.example.coroutine_and_flow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutine_and_flow.model.CountryModel
import com.example.coroutine_and_flow.network.ApiService
import com.example.coroutine_and_flow.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: CountryRepository
): ViewModel() {

    private val _countries = MutableLiveData<List<CountryModel>>()

    val countries : LiveData<List<CountryModel>> = _countries

    init {
        observeCountries()
    }

    private fun observeCountries() {
        viewModelScope.launch {
            repository.getAllCountries()
                .let {
                    _countries.postValue(it)
                }
        }
    }
}