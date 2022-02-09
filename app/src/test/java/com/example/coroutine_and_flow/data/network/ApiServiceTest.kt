package com.example.coroutine_and_flow.data.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.coroutine_and_flow.repository.CountryRepositoryImp
import com.example.coroutine_and_flow.util.MockResponseFileReader
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ApiServiceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockResponse: String
    private lateinit var baseurl: HttpUrl
    private lateinit var apiService: ApiService
    private lateinit var repository: CountryRepositoryImp

    private val gsonBuilder = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        baseurl = mockWebServer.url("https://prayer.aviny.com/api/")
        apiService = Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)

        repository = CountryRepositoryImp(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    //given response ok when fetching results then return a list with elements
    @Test
    fun apiService_successResponse() {
        mockResponse = MockResponseFileReader("response.json").content

        mockWebServer
            .enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(mockResponse)
            )

        val apiResponse = runBlocking { repository.getAllCountries() }
        val jsonApiResponse = gsonBuilder.toJson(apiResponse)

        val resultApiResponse = JsonParser.parseString(jsonApiResponse)
        val expectedResponse = JsonParser.parseString(mockResponse)

        assertNotNull(apiResponse)
        assertTrue(resultApiResponse.equals(expectedResponse))
    }

    /*
    @Test
    fun `given response ok when fetching empty results then return an empty list`() {
        runBlocking {
            mockHttpResponse("json/photos_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = photosRepository.getPhotosFromApi(sol, page)

            assertNotNull(apiResponse)
            assertEquals(apiResponse.extractData?.size, 0)
        }
    }

    @Test
    fun `given response failure when fetching results then return exception`() {
        runBlocking {
            mockHttpResponse(PhotosRepository.GENERAL_ERROR_CODE)
            val apiResponse = photosRepository.getPhotosFromApi(sol, page)

            assertNotNull(apiResponse)
            val expectedValue = Result.Error(Exception(SOMETHING_WRONG))
            assertEquals(expectedValue.exception.message, (apiResponse as Result.Error).exception.message)
        }
    }
     */
}














