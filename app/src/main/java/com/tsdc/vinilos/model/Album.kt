package com.tsdc.vinilos.model

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val genre: String,
    val recordLabel: String,
    val description: String,
    val tracks: List<Track> = emptyList(),
    val performers: List<Artist> = emptyList()
)

data class Track(
    val id: Int,
    val name: String,
    val duration: String
)

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String
)