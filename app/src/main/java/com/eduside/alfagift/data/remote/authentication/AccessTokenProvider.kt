package com.eduside.alfagift.data.remote.authentication

import com.eduside.alfagift.BuildConfig
import com.eduside.alfagift.data.remote.ApiServices
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AccessTokenProvider() {

    private val interceptor = HttpLoggingInterceptor()
    private val apiServices: ApiServices
    private val BASE_URL = "https://newsapi.org/v2/"

    init {
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        apiServices = retrofit.create(ApiServices::class.java)
    }

    fun token(): String {
        return "19a84b51d49a4fb39e93a10f58a13295"
    }

//    fun refreshToken(): Boolean = runBlocking { tryRefresh() }

//    private fun tryRefresh(): Boolean {
//        //        try {
////            val response = apiServices.refreshAccessToken(tokenHolder.refreshToken.toString())
////            result = if (response.code() == 200) {
////                response.body()?.let { tokenHolder.storeToken(it) }
////                true
////            } else {
//                sessionManager.clear()
////                false
////            }
////        } catch (e: Exception) {
////            e.printStackTrace()
////        }
//        return false
//    }
}