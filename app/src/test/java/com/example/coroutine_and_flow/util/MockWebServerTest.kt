package com.example.coroutine_and_flow.util

import com.example.coroutine_and_flow.data.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class MockWebServerTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        this.configureMockWebServer()
    }

    @After
    fun tearDown() {
        this.stopMockWebServer()
    }

    abstract fun isMockWebServerEnabled(): Boolean

    open fun configureMockWebServer() {
        if (isMockWebServerEnabled()) {
            mockWebServer = MockWebServer()
            mockWebServer.start()
        }
    }

    open fun stopMockWebServer() {
        if (isMockWebServerEnabled())
            mockWebServer.shutdown()
    }

    open fun mockHttpResponse(
        path: String, responseCode: Int
    ) = mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(MockResponseFileReader(path).content))

    open fun mockHttpResponse(
        responseCode: Int
    ) = mockWebServer
        .enqueue(
            MockResponse()
                .setResponseCode(responseCode))

    fun provideApiServiceTest(): ApiService {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ApiService::class.java)
    }

}















