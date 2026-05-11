package com.tsdc.vinilos.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.Collector
import com.tsdc.vinilos.network.VinylsApiService
import com.tsdc.vinilos.network.dto.ArtistDto
import com.tsdc.vinilos.repository.CollectorRepository
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
class CollectorViewModelTest {

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

    // ── fetchCollectors ────────────────────────────────────────────────────

    @Test
    fun fetchCollectors_whenSucceeds_publishesCollectorList() = runTest(dispatcher) {
        val viewModel = CollectorViewModel(
            CollectorRepository { FakeVinylsApiService(collectors = fakeCollectors()) }
        )

        viewModel.fetchCollectors()
        advanceUntilIdle()

        assertEquals(2, viewModel.collectors.value?.size)
        assertEquals("Coleccionista 1", viewModel.collectors.value?.first()?.name)
        assertFalse(viewModel.isLoading.value ?: true)
        assertNull(viewModel.error.value)
    }

    @Test
    fun fetchCollectors_whenFails_publishesErrorMessage() = runTest(dispatcher) {
        val viewModel = CollectorViewModel(
            CollectorRepository { FakeVinylsApiService(error = RuntimeException("falla")) }
        )

        viewModel.fetchCollectors()
        advanceUntilIdle()

        assertNotNull(viewModel.error.value)
        assertFalse(viewModel.isLoading.value ?: true)
    }

    @Test
    fun fetchCollectors_setsLoadingTrueDuringFetch() = runTest(dispatcher) {
        val viewModel = CollectorViewModel(
            CollectorRepository { FakeVinylsApiService(collectors = fakeCollectors()) }
        )

        val loadingStates = mutableListOf<Boolean>()
        viewModel.isLoading.observeForever { loadingStates.add(it) }

        viewModel.fetchCollectors()
        advanceUntilIdle()

        assertTrue("isLoading debe haber sido true durante la carga", loadingStates.contains(true))
        assertFalse(viewModel.isLoading.value ?: true)
    }

    // ── fetchCollector (detalle) ───────────────────────────────────────────

    @Test
    fun fetchCollector_whenSucceeds_publishesCollector() = runTest(dispatcher) {
        val viewModel = CollectorViewModel(
            CollectorRepository {
                FakeVinylsApiService(
                    collectors = emptyList(),
                    collectorById = Collector(5, "Especial", "3001234567", "especial@test.com")
                )
            }
        )

        viewModel.fetchCollector(5)
        advanceUntilIdle()

        assertNotNull(viewModel.collector.value)
        assertEquals("Especial", viewModel.collector.value?.name)
        assertFalse(viewModel.isLoading.value ?: true)
    }

    @Test
    fun fetchCollector_whenFails_publishesError() = runTest(dispatcher) {
        val viewModel = CollectorViewModel(
            CollectorRepository { FakeVinylsApiService(error = RuntimeException("no encontrado")) }
        )

        viewModel.fetchCollector(99)
        advanceUntilIdle()

        assertNotNull(viewModel.error.value)
        assertFalse(viewModel.isLoading.value ?: true)
    }

    // ── refresh ────────────────────────────────────────────────────────────

    @Test
    fun refresh_limpiaCacheYRecargaListado() = runTest(dispatcher) {
        var callCount = 0
        val viewModel = CollectorViewModel(
            CollectorRepository {
                FakeVinylsApiService(
                    collectors = fakeCollectors(),
                    onGetCollectors = { callCount++ }
                )
            }
        )

        viewModel.fetchCollectors()
        advanceUntilIdle()
        viewModel.refresh()
        advanceUntilIdle()

        assertEquals("Debe llamar a la API dos veces — init + refresh", 2, callCount)
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private fun fakeCollectors() = listOf(
        Collector(1, "Coleccionista 1", "3001111111", "col1@test.com"),
        Collector(2, "Coleccionista 2", "3002222222", "col2@test.com")
    )

    private class FakeVinylsApiService(
        private val collectors: List<Collector> = emptyList(),
        private val collectorById: Collector? = null,
        private val error: Exception? = null,
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
            error?.let { throw it }
            onGetCollectors()
            return collectors
        }

        override suspend fun getCollector(id: Int): Collector {
            error?.let { throw it }
            return collectorById ?: throw IllegalStateException("No collector for id=$id")
        }
    }
}
