package com.example.legomvvm.util.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.legomvvm.api.LegoService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader

@RunWith(JUnit4::class)
class LegoServiceTest {
    @Rule
    @JvmField
    val instantExecuteRule = InstantTaskExecutorRule()

    private lateinit var service: LegoService

    private lateinit var mockWebService: MockWebServer

    @Before
    fun createService() {
        mockWebService = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebService.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LegoService::class.java)
    }

    @After
    fun stopService() {
        mockWebService.shutdown()
    }

    @Test
    fun requestLegoThemes() {
        runBlocking {
            enqueueResponse("legothemes.json")
            val resultResponse = service.getThemes().body()
            val request = mockWebService.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/lego/themes/"))
        }
    }

    private fun enqueueResponse(fileName: String, header: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val str = inputStream?.bufferedReader()?.use(BufferedReader::readText)
        val mockResponse = MockResponse()
        for ((key, value) in header) {
            mockResponse.addHeader(key, value)
        }

        mockWebService.enqueue(mockResponse.setBody(str.toString()))
    }
}