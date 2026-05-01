package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Artist
import com.tsdc.vinilos.network.NetworkModule
import com.tsdc.vinilos.network.VinylsApiService
import com.tsdc.vinilos.network.mapper.toArtist

class ArtistRepository(
    private val apiProvider: () -> VinylsApiService = { NetworkModule.api }
) {

    suspend fun getArtists(): List<Artist> {
        return apiProvider().getArtists().map { it.toArtist() }
    }
}
