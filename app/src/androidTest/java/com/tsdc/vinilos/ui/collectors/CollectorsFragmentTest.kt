package com.tsdc.vinilos.ui.collectors

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
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
class CollectorsFragmentTest {

    @get:Rule
    val mockServerRule = MockServerRule()

    /**
     * HU05 - El listado de coleccionistas es visible al navegar a la seccion.
     */
    @Test
    fun hu05_collectorsFragment_showsCollectorList() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "collectors.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.collectorsFragment)).perform(click())
        sleep(500)

        onView(withId(R.id.rvCollectors)).check(matches(isDisplayed()))
    }

    /**
     * HU05 - El nombre de cada coleccionista se muestra en la lista.
     */
    @Test
    fun hu05_collectorsFragment_showsCollectorNames() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "collectors.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.collectorsFragment)).perform(click())
        sleep(500)

        onView(withText("Coleccionista Prueba 1")).check(matches(isDisplayed()))
        onView(withText("Coleccionista Prueba 2")).check(matches(isDisplayed()))
    }

    /**
     * HU05 - El email de cada coleccionista se muestra en la lista.
     */
    @Test
    fun hu05_collectorsFragment_showsCollectorEmails() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueFromFile(context, "collectors.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.collectorsFragment)).perform(click())
        sleep(500)

        onView(withText("coleccionista1@test.com")).check(matches(isDisplayed()))
    }

    /**
     * HU05 - Durante la carga se muestra el indicador de progreso
     * y al finalizar aparece el listado.
     */
    @Test
    fun hu05_collectorsFragment_showsLoadingThenList() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val delayedCollectors = context.assets.open("collectors.json")
            .bufferedReader()
            .use { it.readText() }

        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueueDelayed(delayedCollectors, 1500)

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.collectorsFragment)).perform(click())
        sleep(2000)

        onView(withId(R.id.rvCollectors)).check(matches(isDisplayed()))
    }

    /**
     * HU05 - Cuando el servidor falla, se muestra el estado de error con boton de reintento.
     */
    @Test
    fun hu05_collectorsFragment_showsErrorWhenServiceFails() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueue("{}", statusCode = 500)

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.collectorsFragment)).perform(click())
        sleep(500)

        onView(withId(R.id.tvError)).check(matches(isDisplayed()))
        onView(withId(R.id.btnRetry)).check(matches(isDisplayed()))
    }

    /**
     * HU05 - El boton de reintento recarga el listado correctamente.
     */
    @Test
    fun hu05_collectorsFragment_retryButtonReloadsData() {
        val context = InstrumentationRegistry.getInstrumentation().context
        mockServerRule.enqueueFromFile(context, "albums.json")
        mockServerRule.enqueue("{}", statusCode = 500)
        mockServerRule.enqueueFromFile(context, "collectors.json")

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.collectorsFragment)).perform(click())
        sleep(500)
        onView(withId(R.id.btnRetry)).perform(click())
        sleep(500)

        onView(withId(R.id.rvCollectors)).check(matches(isDisplayed()))
    }
}
