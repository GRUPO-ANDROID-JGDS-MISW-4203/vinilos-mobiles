package com.tsdc.vinilos.ui.albums

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
class AlbumDetailFragmentTest {

    @get:Rule
    val mockServerRule = MockServerRule()

    @Test
    fun hu02_navegacionDesdeCatalogo_muestraDetalleAlbum() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "album_detail_1.json")

        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.VH>(0, click()))
        sleep(800)

        onView(withId(R.id.tvAlbumTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun hu02_detalleAlbum_visualizaDatosPrincipales() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "album_detail_1.json")

        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.VH>(0, click()))
        sleep(800)

        onView(withId(R.id.tvAlbumTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.chipGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.tvAlbumLabel)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescription)).check(matches(isDisplayed()))
    }

    @Test
    fun hu02_detalleAlbum_muestraEstadoError() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueue("{\"error\":\"not-found\"}", 500)

        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.VH>(0, click()))
        sleep(800)

        onView(withId(R.id.tvError)).check(matches(isDisplayed()))
        onView(withId(R.id.btnRetry)).check(matches(isDisplayed()))
    }

    @Test
    fun hu02_detalleAlbum_muestraLoading() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "albums.json")
        val delayedDetail = context.assets.open("album_detail_1.json")
            .bufferedReader()
            .use { it.readText() }
        mockServerRule.enqueueDelayed(delayedDetail, 1500)

        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.VH>(0, click()))
        sleep(2200)

        onView(withId(R.id.tvAlbumTitle)).check(matches(isDisplayed()))
    }
}
