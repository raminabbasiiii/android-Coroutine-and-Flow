package com.example.coroutine_and_flow.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutine_and_flow.datasource.network.CountryModel
import com.example.coroutine_and_flow.datasource.network.MainApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject
import kotlin.system.measureTimeMillis

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
        //Parallel background task with async and await
        CoroutineScope(IO).launch {
            val time = measureTimeMillis {
                val result1 = async {
                    service.getCountries()
                }

                val result2 = async {
                    getNumber1()
                }
                result1.await().let {
                    countries.postValue(it)
                }
                println("debug: getNumber1 is: ${result2.await()} ")

            }
            println("debug: job1 and job2 are complete. It took $time ms")

            //Sequential background task with async and await
            /*val time = measureTimeMillis {
                val result1 = async {
                    getNumber1()
                }.await()

                val result2 = async {
                    getNumber2()
                }.await()
            }
            println("debug: job1 and job2 are complete. It took $time ms")*/
        }
    }

    private suspend fun getNumber1(): Int {
        delay(2000)
        return 21
    }

    /*private suspend fun getNumber2(): Int {
        delay(2000)
        return 13
    }*/
}