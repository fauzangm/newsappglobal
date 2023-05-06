package com.eduside.alfagift.base

import okhttp3.mockwebserver.MockResponse
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

object MockWebServerUtil {

    fun enqueueResponse(fileName: String, code: Int = 404): MockResponse {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = inputStream?.let { inputStream.source().buffer() }
        return if (source != null) {
            MockResponse()
                .setResponseCode(code)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(source.readString(StandardCharsets.UTF_8))
        } else {
            MockResponse()
                .setResponseCode(404)
        }
    }

    fun getDummyContent(fileName: String): String? {
        val inputStream = javaClass.classLoader?.getResourceAsStream("dummy/$fileName")
        val source = inputStream?.let { inputStream.source().buffer() }
        return source?.readString(StandardCharsets.UTF_8)
    }
}