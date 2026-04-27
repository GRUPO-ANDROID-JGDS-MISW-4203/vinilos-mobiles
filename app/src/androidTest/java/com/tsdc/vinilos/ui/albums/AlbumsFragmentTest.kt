package com.tsdc.vinilos.ui.albums

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.tsdc.vinilos.MainActivity
import com.tsdc.vinilos.R
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class AlbumsFragmentTest {

    @Test
    fun albumsFragment_showsRecyclerView() {
        launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        sleep(2000)
        onView(withId(R.id.rvAlbums)).check(matches(isDisplayed()))
    }

    @Test
    fun albumsFragment_showsFab() {
        launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        sleep(2000)
        onView(withId(R.id.fabAddAlbum)).check(matches(isDisplayed()))
    }

    @Test
    fun albumsFragment_showsProgressBarInitially() {
        launch(MainActivity::class.java)
        onView(withId(R.id.albumsFragment)).perform(click())
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun albumsFragment_showsBottomNavigation() {
        launch(MainActivity::class.java)
        onView(withId(R.id.bottomNav)).check(matches(isDisplayed()))
    }

    @Test
    fun albumsFragment_showsToolbar() {
        launch(MainActivity::class.java)
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    }
}