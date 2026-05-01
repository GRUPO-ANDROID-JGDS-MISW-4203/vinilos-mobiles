package com.tsdc.vinilos.network.mapper

import com.tsdc.vinilos.model.ArtistDateType
import com.tsdc.vinilos.network.dto.ArtistDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ArtistMapperTest {

    @Test
    fun toArtist_mapsMusicianFields() {
        val dto = ArtistDto(
            id = 7,
            name = "Ruben Blades",
            image = "https://example.com/ruben.jpg",
            description = "Cantautor panameno",
            birthDate = "1948-07-16T00:00:00.000Z",
            creationDate = null
        )

        val artist = dto.toArtist()

        assertEquals(7, artist.id)
        assertEquals("Ruben Blades", artist.name)
        assertEquals("https://example.com/ruben.jpg", artist.image)
        assertEquals("Cantautor panameno", artist.description)
        assertEquals("1948-07-16", artist.date)
        assertEquals(ArtistDateType.BIRTH, artist.dateType)
    }

    @Test
    fun toArtist_usesSafeDefaultsWhenOptionalFieldsAreMissing() {
        val dto = ArtistDto(
            id = 3,
            name = " ",
            image = null,
            description = null,
            birthDate = null,
            creationDate = null
        )

        val artist = dto.toArtist()

        assertEquals("Artista sin nombre", artist.name)
        assertEquals("", artist.image)
        assertEquals("", artist.description)
        assertEquals("", artist.date)
        assertEquals(ArtistDateType.UNKNOWN, artist.dateType)
    }
}
