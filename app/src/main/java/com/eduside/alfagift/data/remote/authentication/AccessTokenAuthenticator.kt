package com.eduside.alfagift.data.remote.authentication

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AccessTokenAuthenticator(
    private val tokenProvider: AccessTokenProvider
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = tokenProvider.token() ?: return null
        synchronized(this) {
            val newToken = tokenProvider.token()

            // Check if the request made was previously made as an authenticated request.
            if (response.request.header("Authorization") != null) {

                // If the token has changed since the request was made, use the new token.
                if (newToken != token) {
                    return response.request
                        .newBuilder()
                        .removeHeader("Authorization")
                        .removeHeader("Accept")
                        .addHeader("Authorization", "Bearer $newToken")
                        .addHeader("Accept", "application/json")
                        .build()
                }

//                if (tokenProvider.refreshToken()) {
//                    val updatedToken = tokenProvider.token()
//
//                    // Retry the request with the new token.
//                    return response.request
//                        .newBuilder()
//                        .removeHeader("Authorization")
//                        .removeHeader("Accept")
//                        .addHeader("Authorization", "Bearer $updatedToken")
//                        .addHeader("Accept", "application/json")
//                        .build()
//                }
            }
        }
        return null
    }
}