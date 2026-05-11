package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.Artist
import com.tsdc.vinilos.network.NetworkModule
import com.tsdc.vinilos.network.VinylsApiService
import com.tsdc.vinilos.network.mapper.toArtist

class ArtistRepository(
    private val apiProvider: () -> VinylsApiService = { NetworkModule.api }
) {

    // Cache de lista completa de artistas
    private var cachedArtists: List<Artist>? = null

    // Cache por id para evitar re-descargar el mismo detalle
    private val artistCache = mutableMapOf<Int, Artist>()

    // Cache de albums — evita descargar todo el catalogo en cada visita al detalle
    private var cachedAlbums: List<Album>? = null

    suspend fun getArtists(): List<Artist> {
        return cachedArtists ?: apiProvider().getArtists()
            .map { it.toArtist() }
            .also { cachedArtists = it }
    }

    suspend fun getArtist(id: Int): Artist {
        return artistCache[id] ?: apiProvider().getArtist(id)
            .toArtist()
            .also { artistCache[id] = it }
    }

    suspend fun getAlbumsByArtist(artistName: String): List<Album> {
        // Reutiliza el cache de albums si ya fue descargado — no hace nueva peticion de red
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
