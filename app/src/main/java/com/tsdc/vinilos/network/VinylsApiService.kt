package com.tsdc.vinilos.network

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.network.dto.ArtistDto
import retrofit2.http.GET
import retrofit2.http.Path

interface VinylsApiService {

    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") id: Int): Album

    @GET("musicians")
    suspend fun getArtists(): List<ArtistDto>
}
