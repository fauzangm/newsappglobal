package com.eduside.alfagift.base

import com.eduside.alfagift.base.MockWebServerUtil.enqueueResponse
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

val mockWebServerDispatcher = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {

            //succes
            "/everything?q=Apple&from=2023-04-10&sortBy=popularity" -> return enqueueResponse("get-berita-200.json", 200)

            // Error
            "/error/everything?q=Apple&from=2023-04-10&sortBy=popularity" -> return enqueueResponse("error-520.json", 520)


        }
        return MockResponse().setResponseCode(404)
    }
}