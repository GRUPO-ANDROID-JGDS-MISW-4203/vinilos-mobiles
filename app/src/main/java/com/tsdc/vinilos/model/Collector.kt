package com.tsdc.vinilos.model

import com.google.gson.annotations.SerializedName

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    @SerializedName("collectorAlbums")
    val favoriteAlbums: List<Album> = emptyList(),

    @SerializedName("favoritePerformers")
    val favoriteArtists: List<Artist> = emptyList()
)
