package com.example.coroutine_and_flow.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutine_and_flow.datasource.network.CountryModel
import com.example.coroutine_and_flow.datasource.network.MainApiService
import com.example.coroutine_and_flow.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val service: MainApiService
): ViewModel() {

    private val JOB_TIMEOUT = 100L
    val countries = MutableLiveData<Resource<List<CountryModel>>>()

    init {
        countryList()
    }

    private fun countryList() {
        CoroutineScope(IO).launch {

            val job = withTimeoutOrNull(JOB_TIMEOUT) {
                service.getCountries()
                    .let {
                        countries.postValue(Resource.success(it))
                    }
            }
            if (job == null) {
                val cancelMessage = "Cancelling job...Job took longer than $JOB_TIMEOUT ms"
                countries.postValue(Resource.error(cancelMessage,null))
            }
        }
    }
}