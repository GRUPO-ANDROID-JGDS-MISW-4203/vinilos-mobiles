package com.tsdc.vinilos.model

data class AlbumRequest(
    val name: String,
    val cover: String,
    val releaseDate: String,
    val genre: String,
    val recordLabel: String,
    val description: String
)