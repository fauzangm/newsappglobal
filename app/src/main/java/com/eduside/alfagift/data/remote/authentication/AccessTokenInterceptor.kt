package com.eduside.alfagift.data.remote.authentication

import androidx.appcompat.app.AppCompatDelegate
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val tokenProvider: AccessTokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.token()

        val response = run {
            val authenticatedRequest = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(authenticatedRequest)
        }

//        if ((response.code == 402 || response.code == 403)) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            if(tokenProvider.refreshToken()) {
//                val updatedToken = tokenProvider.token()
//
//                // Retry the request with the new token.
//                val authenticatedRequest = chain.request()
//                    .newBuilder()
//                    .removeHeader("Authorization")
//                    .removeHeader("Accept")
//                    .addHeader("Authorization", "Bearer $updatedToken")
//                    .addHeader("Accept", "application/json")
//                    .build()
//                response.close()
//                response = chain.proceed(authenticatedRequest)
//            }
//        }

        return response
    }
}