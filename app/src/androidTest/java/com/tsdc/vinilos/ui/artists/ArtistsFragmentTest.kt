package com.tsdc.vinilos.ui.artists

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.tsdc.vinilos.MainActivity
import com.tsdc.vinilos.R
import com.tsdc.vinilos.util.MockServerRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class ArtistsFragmentTest {

    @get:Rule
    val mockServerRule = MockServerRule()

    @Test
    fun hu03_artistsFragment_showsArtistList() {
        val context = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation()
            .context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "artists.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.rvArtists)).check(matches(isDisplayed()))
        onView(withText("Artista Prueba 1")).check(matches(isDisplayed()))
        onView(withText("Artista Prueba 2")).check(matches(isDisplayed()))
    }

    @Test
    fun hu03_artistsFragment_navigatesToArtistDetailOnItemClick() {
        val context = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation()
            .context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "artists.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.rvArtists))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ArtistAdapter.VH>(0, click()))
        onView(withId(R.id.artistDetailRoot)).check(matches(isDisplayed()))
    }

    @Test
    fun hu03_artistsFragment_showsLoadingThenList() {
        val context = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation()
            .context
        val delayedArtists = context.assets.open("artists.json").bufferedReader().use { it.readText() }
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueDelayed(delayedArtists, 1500)

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(1800)
        onView(withId(R.id.rvArtists)).check(matches(isDisplayed()))
    }

    @Test
    fun hu03_artistsFragment_showsErrorWhenServiceFails() {
        val context = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation()
            .context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueue("{}", statusCode = 500)

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.tvArtistsError)).check(matches(isDisplayed()))
        onView(withId(R.id.btnRetryArtists)).check(matches(isDisplayed()))
    }

    @Test
    fun hu03_artistsFragment_showsEmptyStateWhenNoArtistsExist() {
        val context = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation()
            .context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueue("[]")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.tvArtistsEmpty)).check(matches(isDisplayed()))
    }
}
