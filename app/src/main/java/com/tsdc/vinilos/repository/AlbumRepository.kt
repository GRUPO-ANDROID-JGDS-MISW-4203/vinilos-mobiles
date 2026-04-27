package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.network.NetworkModule

class AlbumRepository {

    private val api = NetworkModule.api
    private var cache: List<Album>? = null

    suspend fun getAlbums(): List<Album> {
        // Si ya tenemos datos en caché, los retornamos sin llamar la API
        return cache ?: api.getAlbums().also { cache = it }
    }

    fun clearCache() {
        cache = null
    }
}