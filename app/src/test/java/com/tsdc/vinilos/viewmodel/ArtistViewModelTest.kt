package com.tsdc.vinilos.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.model.Performer
import com.tsdc.vinilos.network.VinylsApiService
import com.tsdc.vinilos.network.dto.ArtistDto
import com.tsdc.vinilos.repository.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArtistViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ── refresh (lista) ────────────────────────────────────────────────────

    @Test
    fun refresh_whenRepositorySucceeds_publishesArtists() = runTest(dispatcher) {
        val viewModel = ArtistViewModel(
            ArtistRepository {
                FakeVinylsApiService(
                    artists = listOf(
                        ArtistDto(1, "Artista Prueba", "https://example.com/artist.jpg",
                            "Descripcion", "1980-01-01", null)
                    )
                )
            }
        )

        viewModel.refresh()
        advanceUntilIdle()

        assertEquals(1, viewModel.artists.value?.size)
        assertEquals("Artista Prueba", viewModel.artists.value?.first()?.name)
        assertFalse(viewModel.isLoading.value ?: true)
        assertNull(viewModel.error.value)
    }

    @Test
    fun refresh_whenRepositoryFails_publishesErrorAndEmptyList() = runTest(dispatcher) {
        val viewModel = ArtistViewModel(
            ArtistRepository { FakeVinylsApiService(error = IllegalStateException("boom")) }
        )

        viewModel.refresh()
        advanceUntilIdle()

        assertEquals(0, viewModel.artists.value?.size)
        assertFalse(viewModel.isLoading.value ?: true)
        assertNotNull(viewModel.error.value)
    }

    @Test
    fun refresh_setsLoadingTrueDuringFetch() = runTest(dispatcher) {
        val viewModel = ArtistViewModel(
            ArtistRepository {
                FakeVinylsApiService(
                    artists = listOf(ArtistDto(1, "Queen", null, null, null, "1970-01-01"))
                )
            }
        )

        val loadingStates = mutableListOf<Boolean>()
        viewModel.isLoading.observeForever { loadingStates.add(it) }

        viewModel.refresh()
        advanceUntilIdle()

        assertTrue("isLoading debe haber sido true durante la carga", loadingStates.contains(true))
        assertFalse(viewModel.isLoading.value ?: true)
    }

    // ── fetchArtistDetail ──────────────────────────────────────────────────

    @Test
    fun fetchArtistDetail_whenSucceeds_publishesArtist() = runTest(dispatcher) {
        val viewModel = ArtistViewModel(
            ArtistRepository {
                FakeVinylsApiService(
                    artistById = ArtistDto(5, "Ruben Blades", null, "Cantautor", "1948-07-16", null)
                )
            }
        )

        viewModel.fetchArtistDetail(5)
        advanceUntilIdle()

        assertNotNull(viewModel.artist.value)
        assertEquals("Ruben Blades", viewModel.artist.value?.name)
        assertFalse(viewModel.isLoading.value ?: true)
        assertNull(viewModel.error.value)
    }

    @Test
    fun fetchArtistDetail_whenFails_publishesError() = runTest(dispatcher) {
        val viewModel = ArtistViewModel(
            ArtistRepository { FakeVinylsApiService(error = RuntimeException("error red")) }
        )

        viewModel.fetchArtistDetail(99)
        advanceUntilIdle()

        assertNull(viewModel.artist.value)
        assertNotNull(viewModel.error.value)
        assertFalse(viewModel.isLoading.value ?: true)
    }

    // ── fetchArtistAlbums ──────────────────────────────────────────────────

    @Test
    fun fetchArtistAlbums_whenSucceeds_publishesFilteredAlbums() = runTest(dispatcher) {
        val viewModel = ArtistViewModel(
            ArtistRepository {
                FakeVinylsApiService(
                    albums = listOf(
                        albumWith(1, "Queen"),
                        albumWith(2, "Ruben Blades"),
                        albumWith(3, "Queen")
                    )
                )
            }
        )

        viewModel.fetchArtistAlbums("Queen")
        advanceUntilIdle()

        assertEquals(2, viewModel.artistAlbums.value?.size)
        assertFalse(viewModel.albumsLoading.value ?: true)
    }

    @Test
    fun fetchArtistAlbums_whenFails_publishesEmptyList() = runTest(dispatcher) {
        val viewModel = ArtistViewModel(
            ArtistRepository { FakeVinylsApiService(error = RuntimeException("falla")) }
        )

        viewModel.fetchArtistAlbums("Queen")
        advanceUntilIdle()

        assertTrue(viewModel.artistAlbums.value?.isEmpty() ?: false)
        assertFalse(viewModel.albumsLoading.value ?: true)
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private fun albumWith(id: Int, performer: String) = Album(
        id = id, name = "Album $id", cover = "", releaseDate = "2020-01-01",
        description = "", genre = "Rock", recordLabel = "EMI",
        performers = listOf(Performer(id, performer)), tracks = emptyList()
    )

    private class FakeVinylsApiService(
        private val artists: List<ArtistDto> = emptyList(),
        private val artistById: ArtistDto? = null,
        private val albums: List<Album> = emptyList(),
        private val error: Exception? = null
    ) : VinylsApiService {

        override suspend fun getAlbums(): List<Album> {
            error?.let { throw it }
            return albums
        }

        override suspend fun getAlbum(id: Int): Album =
            throw UnsupportedOperationException()

        override suspend fun getArtists(): List<ArtistDto> {
            error?.let { throw it }
            return artists
        }

        override suspend fun getArtist(id: Int): ArtistDto {
            error?.let { throw it }
            return artistById ?: throw IllegalStateException("No artist for id=$id")
        }

        override suspend fun getCollectors(): List<Collector> =
            throw UnsupportedOperationException()

        override suspend fun getCollector(id: Int): Collector =
            throw UnsupportedOperationException()
    }
}
