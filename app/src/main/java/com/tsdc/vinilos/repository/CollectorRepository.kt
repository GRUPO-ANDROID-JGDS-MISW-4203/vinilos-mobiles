package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.network.NetworkModule

class CollectorRepository {

    private val api = NetworkModule.api
    private var cache: List<Collector>? = null

    suspend fun getCollectors(): List<Collector> {
        return cache ?: api.getCollectors().also { cache = it }
    }

    suspend fun getCollector(id: Int): Collector {
        return cache?.find { it.id == id } ?: api.getCollector(id)
    }

    fun clearCache() {
        cache = null
    }
}