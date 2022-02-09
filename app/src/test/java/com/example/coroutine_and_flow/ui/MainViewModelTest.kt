package com.example.coroutine_and_flow.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.coroutine_and_flow.MainCoroutinesRule
import com.example.coroutine_and_flow.data.network.responses.Country
import com.example.coroutine_and_flow.getOrAwaitValue
import com.example.coroutine_and_flow.repository.CountryRepository
import com.example.coroutine_and_flow.util.Event
import com.example.coroutine_and_flow.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.doReturn
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var repository: CountryRepository

    @Mock
    lateinit var countryResponseObserver: Observer<Resource<List<Country>>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `when fetching results ok then return a list successfully`() {
        runBlockingTest {
            val res = Resource.success(emptyList<Country>())
            viewModel.countries.observeForever(countryResponseObserver)
            given(repository.getAllCountries()).willReturn(emptyList())
            verify(repository).getAllCountries()
            viewModel.getCountries()
            assertNotNull(viewModel.countries.value)
            assertEquals(res,viewModel.countries.getOrAwaitValue())
        }
    }

    /*
     @Test
    fun `when calling for results then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.getPhotos().observeForever(photosResponseObserver)
            viewModel.loadData()
            verify(photosResponseObserver).onChanged(Result.InProgress)
        }
    }

    @Test
    fun `when fetching results fails then return an error`() {
        val exception = mock(HttpException::class.java)
        testCoroutineRule.runBlockingTest {
            viewModel.getPhotos().observeForever(photosResponseObserver)
            whenever(photosRepository.getPhotosFromApi(anyInt(), anyInt())).thenAnswer {
                Result.Error(exception)
            }
            viewModel.loadData()
            assertNotNull(viewModel.getPhotos().value)
            assertEquals(Result.Error(exception), viewModel.getPhotos().value)
        }
    }
     */
}















