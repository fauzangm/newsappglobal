package com.eduside.alfagift.base

import com.eduside.alfagift.data.remote.ApiServices
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseTest {

    lateinit var mockWebServer: MockWebServer

    @Before
    open fun setup() {
        this.configureMockServer()
    }

    @After
    open fun teardown() {
        this.stopMockServer()
    }

    // MOCK SERVER
    abstract fun isMockServerEnabled(): Boolean // Because we don't want it always enabled on all tests

    open fun configureMockServer() {
        if (isMockServerEnabled()) {
            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = mockWebServerDispatcher
            mockWebServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockWebServer.shutdown()
        }
    }

    // RETROFIT CLIENT
    fun getClient(baseUrl: HttpUrl): ApiServices {
        val apiService: ApiServices
        val buildHttpClient = getNewHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient)
            .build()
        apiService = retrofit.create(ApiServices::class.java)
        return apiService
    }

    private fun getNewHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(interceptor)
        client
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)

        return client.build()
    }
}
