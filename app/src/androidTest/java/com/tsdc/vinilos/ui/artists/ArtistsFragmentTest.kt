package com.tsdc.vinilos.ui.artists

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
class ArtistsFragmentTest {

    @Test
    fun artistsFragment_showsRecyclerView() {
        launch(MainActivity::class.java)
        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(2000)
        onView(withId(R.id.rvArtists)).check(matches(isDisplayed()))
    }

    @Test
    fun artistsFragment_showsPlaceholderText() {
        launch(MainActivity::class.java)
        onView(withId(R.id.artistsFragment)).perform(click())
        sleep(2000)
        onView(withId(R.id.tvPlaceholder)).check(matches(isDisplayed()))
    }
}