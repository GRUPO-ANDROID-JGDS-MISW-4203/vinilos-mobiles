package com.tsdc.vinilos.ui.albums

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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
class AlbumsFragmentTest {

    @get:Rule
    val mockServerRule = MockServerRule()

    @Test
    fun albumsFragment_showsRecyclerView() {
        mockServerRule.enqueueFromFile(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().context,
            "albums.json"
        )
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        onView(withId(R.id.rvAlbums)).check(matches(isDisplayed()))
    }

    @Test
    fun albumsFragment_navigatesToAlbumDetailOnItemClick() {
        val context = androidx.test.platform.app.InstrumentationRegistry
            .getInstrumentation()
            .context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "album_detail_1.json")
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        onView(withId(R.id.rvAlbums))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.VH>(0, click()))
        onView(withId(R.id.tvAlbumTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun albumsFragment_showsProgressBarInitially() {
        val context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().context
        val delayedAlbums = context.assets.open("albums.json").bufferedReader().use { it.readText() }
        mockServerRule.enqueueDelayed(delayedAlbums, 2000)
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        sleep(2300)
        onView(withId(R.id.rvAlbums)).check(matches(isDisplayed()))
    }

    @Test
    fun albumsFragment_showsBottomNavigation() {
        mockServerRule.enqueueFromFile(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().context,
            "albums.json"
        )
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.bottomNav)).check(matches(isDisplayed()))
    }

    @Test
    fun albumsFragment_showsToolbar() {
        mockServerRule.enqueueFromFile(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().context,
            "albums.json"
        )
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    }
}