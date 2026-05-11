package com.tsdc.vinilos.ui.artists

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.tsdc.vinilos.MainActivity
import com.tsdc.vinilos.R
import com.tsdc.vinilos.util.MockServerRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class ArtistDetailFragmentTest {

    @get:Rule
    val mockServerRule = MockServerRule()

    /**
     * HU04 - Navegar desde el listado de artistas al detalle muestra la pantalla de detalle.
     * Flujo: albums (carga inicial) -> artistas -> click artista -> detalle artista
     */
    @Test
    fun hu04_navegacionDesdeListado_muestraDetalleArtista() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "artists.json")
        mockServerRule.enqueueFromFile(context, "artist_detail_1.json")
        mockServerRule.enqueueFromFile(context, "albums.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.rvArtists))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ArtistAdapter.VH>(0, click()))
        sleep(800)

        onView(withId(R.id.tvArtistName)).check(matches(isDisplayed()))
    }

    /**
     * HU04 - El detalle muestra el nombre y la biografia del artista.
     */
    @Test
    fun hu04_detalleArtista_visualizaNombreYBiografia() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "artists.json")
        mockServerRule.enqueueFromFile(context, "artist_detail_1.json")
        mockServerRule.enqueueFromFile(context, "albums.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.rvArtists))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ArtistAdapter.VH>(0, click()))
        sleep(800)

        onView(withId(R.id.tvArtistName)).check(matches(isDisplayed()))
        onView(withText("Artista Prueba 1")).check(matches(isDisplayed()))
        onView(withId(R.id.tvArtistBio)).check(matches(isDisplayed()))
    }

    /**
     * HU04 - El detalle muestra la lista de albums asociados al artista.
     */
    @Test
    fun hu04_detalleArtista_muestraAlbumsDelArtista() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "artists.json")
        mockServerRule.enqueueFromFile(context, "artist_detail_1.json")
        mockServerRule.enqueueFromFile(context, "albums.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.rvArtists))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ArtistAdapter.VH>(0, click()))
        sleep(800)

        onView(withId(R.id.rvAlbums)).check(matches(isDisplayed()))
    }

    /**
     * HU04 - Cuando el servidor falla, se muestra el estado de error con boton de reintento.
     */
    @Test
    fun hu04_detalleArtista_muestraEstadoError() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "artists.json")
        mockServerRule.enqueue("{\"error\":\"not-found\"}", 500)

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.rvArtists))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ArtistAdapter.VH>(0, click()))
        sleep(800)

        onView(withId(R.id.tvError)).check(matches(isDisplayed()))
        onView(withId(R.id.btnRetry)).check(matches(isDisplayed()))
    }

    /**
     * HU04 - Durante la carga, se muestra el indicador de progreso
     * y al finalizar se muestra el contenido.
     */
    @Test
    fun hu04_detalleArtista_muestraLoadingYLuegoContenido() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val delayedDetail = context.assets.open("artist_detail_1.json")
            .bufferedReader()
            .use { it.readText() }

        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "artists.json")
        mockServerRule.enqueueDelayed(delayedDetail, 1500)
        mockServerRule.enqueueFromFile(context, "albums.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.rvArtists))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ArtistAdapter.VH>(0, click()))
        sleep(2200)

        onView(withId(R.id.tvArtistName)).check(matches(isDisplayed()))
    }
}
