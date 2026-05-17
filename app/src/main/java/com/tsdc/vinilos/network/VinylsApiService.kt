package com.tsdc.vinilos.network

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.AlbumRequest
import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.model.Track
import com.tsdc.vinilos.model.TrackRequest
import com.tsdc.vinilos.network.dto.ArtistDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VinylsApiService {

    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") id: Int): Album

    @POST("albums")
    suspend fun createAlbum(
        @Body album: AlbumRequest
    ): Album

    @POST("albums/{albumId}/tracks")
    suspend fun addTrack(
        @Path("albumId") albumId: Int,
        @Body track: TrackRequest
    ): Track

    @GET("musicians")
    suspend fun getArtists(): List<ArtistDto>

    @GET("musicians/{id}")
    suspend fun getArtist(@Path("id") id: Int): ArtistDto

    @GET("collectors")
    suspend fun getCollectors(): List<Collector>

    @GET("collectors/{id}")
    suspend fun getCollector(@Path("id") id: Int): Collector
}