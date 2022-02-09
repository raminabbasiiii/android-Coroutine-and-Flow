package com.example.coroutine_and_flow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutine_and_flow.data.network.responses.Country
import com.example.coroutine_and_flow.repository.CountryRepository
import com.example.coroutine_and_flow.util.Event
import com.example.coroutine_and_flow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: CountryRepository
): ViewModel() {

    private val _countries = MutableLiveData<Resource<List<Country>>>()

    val countries : LiveData<Resource<List<Country>>> = _countries

    init {
        getCountries()
    }

    fun getCountries() {
        viewModelScope.launch {
            repository.getAllCountries()
                .let {
                    _countries.postValue(Resource.success(it))
                }
        }
    }
}