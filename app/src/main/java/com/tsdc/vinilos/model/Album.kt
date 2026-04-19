package com.tsdc.vinilos.model

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val performers: List<Performer> = emptyList(),
    val tracks: List<Track> = emptyList()
)