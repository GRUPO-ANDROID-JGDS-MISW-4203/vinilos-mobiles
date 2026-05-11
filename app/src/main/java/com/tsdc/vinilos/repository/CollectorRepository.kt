package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.network.NetworkModule
import com.tsdc.vinilos.network.VinylsApiService

class CollectorRepository(
    private val apiProvider: () -> VinylsApiService = { NetworkModule.api }
) {

    private var cache: List<Collector>? = null

    suspend fun getCollectors(): List<Collector> {
        return cache ?: apiProvider().getCollectors().also { cache = it }
    }

    suspend fun getCollector(id: Int): Collector {
        return cache?.find { it.id == id } ?: apiProvider().getCollector(id)
    }

    fun clearCache() {
        cache = null
    }
}