package com.tsdc.vinilos.model

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val date: String,
    val dateType: ArtistDateType
)

enum class ArtistDateType {
    BIRTH,
    CREATION,
    UNKNOWN
}
