package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.ArtistDateType
import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.network.VinylsApiService
import com.tsdc.vinilos.network.dto.ArtistDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ArtistRepositoryTest {

    @Test
    fun getArtists_returnsMappedArtistsFromApi() = runTest {
        val repository = ArtistRepository {
            FakeVinylsApiService(
                artists = listOf(
                    ArtistDto(
                        id = 1,
                        name = "Queen",
                        image = "https://example.com/queen.jpg",
                        description = "Banda britanica",
                        birthDate = null,
                        creationDate = "1970-01-01T00:00:00.000Z"
                    )
                )
            )
        }

        val artists = repository.getArtists()

        assertEquals(1, artists.size)
        assertEquals("Queen", artists.first().name)
        assertEquals("1970-01-01", artists.first().date)
        assertEquals(ArtistDateType.CREATION, artists.first().dateType)
    }

    private class FakeVinylsApiService(
        private val artists: List<ArtistDto>
    ) : VinylsApiService {
        override suspend fun getAlbums(): List<Album> = emptyList()

        override suspend fun getAlbum(id: Int): Album {
            throw UnsupportedOperationException("Album detail is not needed for artist tests")
        }

        override suspend fun getArtists(): List<ArtistDto> = artists
        override suspend fun getArtist(id: Int): ArtistDto {
            TODO("Not yet implemented")
        }

        override suspend fun getCollectors(): List<Collector> {
            TODO("Not yet implemented")
        }

        override suspend fun getCollector(id: Int): Collector {
            TODO("Not yet implemented")
        }
    }
}
