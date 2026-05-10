package com.tsdc.vinilos.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.Collector
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

    @Test
    fun refresh_whenRepositorySucceeds_publishesArtists() = runTest(dispatcher) {
        val viewModel = ArtistViewModel(
            ArtistRepository {
                FakeVinylsApiService(
                    artists = listOf(
                        ArtistDto(
                            id = 1,
                            name = "Artista Prueba",
                            image = "https://example.com/artist.jpg",
                            description = "Descripcion",
                            birthDate = "1980-01-01",
                            creationDate = null
                        )
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

    private class FakeVinylsApiService(
        private val artists: List<ArtistDto> = emptyList(),
        private val error: Exception? = null
    ) : VinylsApiService {
        override suspend fun getAlbums(): List<Album> = emptyList()

        override suspend fun getAlbum(id: Int): Album {
            throw UnsupportedOperationException("Album detail is not needed for artist tests")
        }

        override suspend fun getArtists(): List<ArtistDto> {
            error?.let { throw it }
            return artists
        }

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
