package com.tsdc.vinilos.network.mapper

import com.tsdc.vinilos.model.Artist
import com.tsdc.vinilos.model.ArtistDateType
import com.tsdc.vinilos.network.dto.ArtistDto

fun ArtistDto.toArtist(): Artist {
    val resolvedDate = birthDate ?: creationDate.orEmpty()
    val resolvedDateType = when {
        birthDate != null -> ArtistDateType.BIRTH
        creationDate != null -> ArtistDateType.CREATION
        else -> ArtistDateType.UNKNOWN
    }

    return Artist(
        id = id,
        name = name?.takeIf { it.isNotBlank() } ?: "Artista sin nombre",
        image = image.orEmpty(),
        description = description.orEmpty(),
        date = resolvedDate.take(10),
        dateType = resolvedDateType
    )
}
