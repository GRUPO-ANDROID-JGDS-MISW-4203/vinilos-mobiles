package com.tsdc.vinilos.network

import com.tsdc.vinilos.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val DEFAULT_BASE_URL = "https://backvynils-q6yc.onrender.com/"

    // Cliente HTTP singleton — se crea una sola vez en toda la vida de la app
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BASIC
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    // API de produccion — singleton, se crea una sola vez
    private val productionApi: VinylsApiService by lazy {
        buildApi(DEFAULT_BASE_URL)
    }

    // API de pruebas — solo se asigna en tests via setBaseUrlForTesting()
    @Volatile
    private var testingApi: VinylsApiService? = null

    // Punto de acceso unico: devuelve testingApi en tests, productionApi en produccion
    val api: VinylsApiService
        get() = testingApi ?: productionApi

    fun setBaseUrlForTesting(url: String) {
        testingApi = buildApi(url)
    }

    fun resetBaseUrl() {
        testingApi = null
    }

    private fun buildApi(baseUrl: String): VinylsApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VinylsApiService::class.java)
}