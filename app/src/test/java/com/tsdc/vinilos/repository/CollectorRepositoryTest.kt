package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.network.VinylsApiService
import com.tsdc.vinilos.network.dto.ArtistDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CollectorRepositoryTest {

    // ── getCollectors ──────────────────────────────────────────────────────

    @Test
    fun getCollectors_returnsCollectorsFromApi() = runTest {
        val repository = CollectorRepository { FakeVinylsApiService(collectors = fakeCollectors()) }

        val result = repository.getCollectors()

        assertEquals(2, result.size)
        assertEquals("Coleccionista 1", result.first().name)
    }

    @Test
    fun getCollectors_secondCall_returnsCachedResult() = runTest {
        var callCount = 0
        val repository = CollectorRepository {
            FakeVinylsApiService(
                collectors = fakeCollectors(),
                onGetCollectors = { callCount++ }
            )
        }

        repository.getCollectors()
        repository.getCollectors()

        assertEquals("Debe llamar a la API solo una vez", 1, callCount)
    }

    @Test
    fun getCollectors_afterClearCache_callsApiAgain() = runTest {
        var callCount = 0
        val repository = CollectorRepository {
            FakeVinylsApiService(
                collectors = fakeCollectors(),
                onGetCollectors = { callCount++ }
            )
        }

        repository.getCollectors()
        repository.clearCache()
        repository.getCollectors()

        assertEquals("Debe llamar a la API dos veces tras limpiar cache", 2, callCount)
    }

    // ── getCollector (detalle) ─────────────────────────────────────────────

    @Test
    fun getCollector_whenInCache_returnsCachedCollector() = runTest {
        val repository = CollectorRepository { FakeVinylsApiService(collectors = fakeCollectors()) }

        repository.getCollectors()
        val result = repository.getCollector(1)

        assertEquals(1, result.id)
        assertEquals("Coleccionista 1", result.name)
    }

    @Test
    fun getCollector_whenNotInCache_fetchesFromApi() = runTest {
        val repository = CollectorRepository {
            FakeVinylsApiService(
                collectors = emptyList(),
                collectorById = Collector(99, "Directo", "123", "directo@test.com")
            )
        }

        val result = repository.getCollector(99)

        assertEquals(99, result.id)
        assertEquals("Directo", result.name)
    }

    @Test
    fun getCollectors_whenApiReturnsEmpty_returnsEmptyList() = runTest {
        val repository = CollectorRepository { FakeVinylsApiService(collectors = emptyList()) }

        val result = repository.getCollectors()

        assertTrue(result.isEmpty())
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private fun fakeCollectors() = listOf(
        Collector(1, "Coleccionista 1", "3001111111", "col1@test.com"),
        Collector(2, "Coleccionista 2", "3002222222", "col2@test.com")
    )

    private class FakeVinylsApiService(
        private val collectors: List<Collector> = emptyList(),
        private val collectorById: Collector? = null,
        private val onGetCollectors: () -> Unit = {}
    ) : VinylsApiService {

        override suspend fun getAlbums(): List<Album> = emptyList()

        override suspend fun getAlbum(id: Int): Album =
            throw UnsupportedOperationException()

        override suspend fun getArtists(): List<ArtistDto> =
            throw UnsupportedOperationException()

        override suspend fun getArtist(id: Int): ArtistDto =
            throw UnsupportedOperationException()

        override suspend fun getCollectors(): List<Collector> {
            onGetCollectors()
            return collectors
        }

        override suspend fun getCollector(id: Int): Collector =
            collectorById ?: throw IllegalStateException("No collector for id=$id")
    }
}
