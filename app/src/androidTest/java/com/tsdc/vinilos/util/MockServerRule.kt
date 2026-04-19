package com.tsdc.vinilos.util

import android.content.Context
import com.tsdc.vinilos.network.NetworkModule
import com.tsdc.vinilos.network.VinylsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MockServerRule : TestWatcher() {

    lateinit var server: MockWebServer
        private set

    lateinit var api: VinylsApiService
        private set

    private var _baseUrl: String = ""

    override fun starting(description: Description) {
        super.starting(description)
        server = MockWebServer()
        server.start()
        _baseUrl = server.url("/").toString()

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        api = Retrofit.Builder()
            .baseUrl(_baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(VinylsApiService::class.java)
    }

    override fun finished(description: Description) {
        super.finished(description)
        server.shutdown()
    }

    fun enqueue(jsonResponse: String, statusCode: Int = 200) {
        server.enqueue(
            MockResponse()
                .setResponseCode(statusCode)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
        )
    }

    fun enqueueFromFile(context: Context, fileName: String, statusCode: Int = 200) {
        val inputStream = context.assets.open(fileName)
        val jsonResponse = inputStream.bufferedReader().use { it.readText() }
        enqueue(jsonResponse, statusCode)
    }
}