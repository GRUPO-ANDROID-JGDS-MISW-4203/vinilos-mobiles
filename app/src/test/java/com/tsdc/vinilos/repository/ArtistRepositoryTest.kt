package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.ArtistDateType
import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.model.Performer
import com.tsdc.vinilos.network.VinylsApiService
import com.tsdc.vinilos.network.dto.ArtistDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ArtistRepositoryTest {

    // ── getArtists ─────────────────────────────────────────────────────────

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

    @Test
    fun getArtists_secondCall_returnsCachedResult() = runTest {
        var callCount = 0
        val repository = ArtistRepository {
            FakeVinylsApiService(
                artists = listOf(
                    ArtistDto(1, "Queen", null, null, null, "1970-01-01")
                ),
                onGetArtists = { callCount++ }
            )
        }

        repository.getArtists()
        repository.getArtists()

        assertEquals("Debe llamar a la API solo una vez", 1, callCount)
    }

    @Test
    fun getArtists_afterClearCache_callsApiAgain() = runTest {
        var callCount = 0
        val repository = ArtistRepository {
            FakeVinylsApiService(
                artists = listOf(ArtistDto(1, "Queen", null, null, null, "1970-01-01")),
                onGetArtists = { callCount++ }
            )
        }

        repository.getArtists()
        repository.clearCache()
        repository.getArtists()

        assertEquals("Debe llamar a la API dos veces tras limpiar cache", 2, callCount)
    }

    // ── getArtist (detalle) ────────────────────────────────────────────────

    @Test
    fun getArtist_returnsMappedArtist() = runTest {
        val repository = ArtistRepository {
            FakeVinylsApiService(
                artistById = ArtistDto(
                    id = 5,
                    name = "Ruben Blades",
                    image = "https://example.com/ruben.jpg",
                    description = "Cantautor panameno",
                    birthDate = "1948-07-16T00:00:00.000Z",
                    creationDate = null
                )
            )
        }

        val artist = repository.getArtist(5)

        assertEquals(5, artist.id)
        assertEquals("Ruben Blades", artist.name)
        assertEquals(ArtistDateType.BIRTH, artist.dateType)
    }

    @Test
    fun getArtist_secondCallSameId_returnsCached() = runTest {
        var callCount = 0
        val repository = ArtistRepository {
            FakeVinylsApiService(
                artistById = ArtistDto(1, "Queen", null, null, null, "1970-01-01"),
                onGetArtist = { callCount++ }
            )
        }

        repository.getArtist(1)
        repository.getArtist(1)

        assertEquals("Debe llamar a la API solo una vez para el mismo id", 1, callCount)
    }

    // ── getAlbumsByArtist ──────────────────────────────────────────────────

    @Test
    fun getAlbumsByArtist_filtraAlbumsPorNombreDeArtista() = runTest {
        val repository = ArtistRepository {
            FakeVinylsApiService(
                albums = listOf(
                    albumWith(id = 1, performer = "Queen"),
                    albumWith(id = 2, performer = "Ruben Blades"),
                    albumWith(id = 3, performer = "Queen")
                )
            )
        }

        val result = repository.getAlbumsByArtist("Queen")

        assertEquals(2, result.size)
        assertTrue(result.all { album -> album.performers.any { it.name == "Queen" } })
    }

    @Test
    fun getAlbumsByArtist_segundaLlamada_reutilizaCacheDeAlbums() = runTest {
        var callCount = 0
        val repository = ArtistRepository {
            FakeVinylsApiService(
                albums = listOf(albumWith(id = 1, performer = "Queen")),
                onGetAlbums = { callCount++ }
            )
        }

        repository.getAlbumsByArtist("Queen")
        repository.getAlbumsByArtist("Queen")

        assertEquals("Albums deben descargarse solo una vez", 1, callCount)
    }

    @Test
    fun getAlbumsByArtist_devuelveListaVacia_cuandoNoHayCoincidencias() = runTest {
        val repository = ArtistRepository {
            FakeVinylsApiService(
                albums = listOf(albumWith(id = 1, performer = "Otro Artista"))
            )
        }

        val result = repository.getAlbumsByArtist("Queen")

        assertTrue(result.isEmpty())
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private fun albumWith(id: Int, performer: String) = Album(
        id = id,
        name = "Album $id",
        cover = "",
        releaseDate = "2020-01-01",
        description = "",
        genre = "Rock",
        recordLabel = "EMI",
        performers = listOf(Performer(id, performer)),
        tracks = emptyList()
    )

    private class FakeVinylsApiService(
        private val artists: List<ArtistDto> = emptyList(),
        private val artistById: ArtistDto? = null,
        private val albums: List<Album> = emptyList(),
        private val onGetArtists: () -> Unit = {},
        private val onGetArtist: () -> Unit = {},
        private val onGetAlbums: () -> Unit = {}
    ) : VinylsApiService {

        override suspend fun getAlbums(): List<Album> {
            onGetAlbums()
            return albums
        }

        override suspend fun getAlbum(id: Int): Album =
            throw UnsupportedOperationException()

        override suspend fun getArtists(): List<ArtistDto> {
            onGetArtists()
            return artists
        }

        override suspend fun getArtist(id: Int): ArtistDto {
            onGetArtist()
            return artistById ?: throw IllegalStateException("No artist configured for id=$id")
        }

        override suspend fun getCollectors(): List<Collector> =
            throw UnsupportedOperationException()

        override suspend fun getCollector(id: Int): Collector =
            throw UnsupportedOperationException()
    }
}
