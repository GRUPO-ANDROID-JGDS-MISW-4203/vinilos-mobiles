package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Album
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

    suspend fun getArtist(id: Int): Artist {
        return apiProvider().getArtist(id).toArtist()
    }

    suspend fun getAlbumsByArtist(artistName: String): List<Album> {
        val allAlbums = apiProvider().getAlbums()
        val normalizedName = artistName.trim().lowercase()

        allAlbums.forEach { album ->
            val performerNames = album.performers.joinToString(", ") { it.name }
            android.util.Log.d("ArtistRepository", "Album '${album.name}' -> performers: [$performerNames]")
        }
        android.util.Log.d("ArtistRepository", "Filtrando por: '$normalizedName'")

        return allAlbums.filter { album ->
            album.performers.any { it.name.trim().lowercase() == normalizedName }
        }.also { result ->
            android.util.Log.d("ArtistRepository", "Albumes encontrados: ${result.size}")
        }
    }
}
