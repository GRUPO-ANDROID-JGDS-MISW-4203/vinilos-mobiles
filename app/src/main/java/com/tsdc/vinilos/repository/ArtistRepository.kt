package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.Artist
import com.tsdc.vinilos.network.NetworkModule
import com.tsdc.vinilos.network.VinylsApiService
import com.tsdc.vinilos.network.mapper.toArtist

class ArtistRepository(
    private val apiProvider: () -> VinylsApiService = { NetworkModule.api }
) {

    private var cachedArtists: List<Artist>? = null
    private val artistCache = mutableMapOf<Int, Artist>()
    private var cachedAlbums: List<Album>? = null

    suspend fun getArtists(): List<Artist> {
        return cachedArtists ?: apiProvider().getArtists()
            .map { it.toArtist() }.also { cachedArtists = it }
    }

    suspend fun getArtist(id: Int): Artist {
        return artistCache[id] ?: apiProvider().getArtist(id)
            .toArtist().also { artistCache[id] = it }
    }

    suspend fun getAlbumsByArtist(artistName: String): List<Album> {
        val albums = cachedAlbums ?: apiProvider().getAlbums().also { cachedAlbums = it }
        val normalizedName = artistName.trim().lowercase()
        return albums.filter { album ->
            album.performers.any { it.name.trim().lowercase() == normalizedName }
        }
    }

    fun clearCache() {
        cachedArtists = null
        artistCache.clear()
        cachedAlbums = null
    }
}
