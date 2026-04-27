package com.tsdc.vinilos.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val DEFAULT_BASE_URL = "https://backvynils-q6yc.onrender.com/"
    @Volatile
    private var baseUrl: String = DEFAULT_BASE_URL

    fun setBaseUrlForTesting(url: String) {
        baseUrl = url
    }

    fun resetBaseUrl() {
        baseUrl = DEFAULT_BASE_URL
    }

    val api: VinylsApiService
        get() {
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(VinylsApiService::class.java)
        }
}