---
name: espresso-testing
description: Crear pruebas E2E con Espresso para Android. Usa este skill cuando necesites escribir pruebas de interfaz gráfica, automatizar escenarios de usuario, o verificar comportamiento de la app. Aplica a Espresso, UI testing, Android testing, E2E tests.
---

# Espresso Testing Skill

## Configuración

### Dependencias

```kotlin
// build.gradle (app)
android {
    defaultConfig {
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    androidTestImplementation 'androidx.test.espresso:espresso-contrib:3.5.1'
    androidTestImplementation 'androidx.test.espresso:espresso-intents:3.5.1'
    androidTestImplementation 'androidx.test:runner:1.5.2'
    androidTestImplementation 'androidx.test:rules:1.5.0'
    androidTestImplementation 'androidx.fragment:fragment-testing:1.6.2'
}
```

## Estructura de Pruebas

### Archivo Base

```kotlin
// androidTest/java/com/tsdc/vinilos/AlbumsE2ETest.kt
@RunWith(AndroidJUnit4::class)
@LargeTest
class AlbumsE2ETest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        // Registrar IdlingResource para esperar llamadas de red
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun hu01_consultarCatalogoAlbumes_muestraListaAlbumes() {
        // Given: El usuario está en la pantalla principal
        
        // When: El usuario navega a la sección de álbumes
        onView(withId(R.id.nav_albums)).perform(click())
        
        // Then: Se muestra el listado de álbumes
        onView(withId(R.id.recycler_albums))
            .check(matches(isDisplayed()))
        onView(withId(R.id.recycler_albums))
            .check(matches(hasMinimumChildCount(1)))
    }
}
```

## Patrones de Prueba

### 1. Verificar Elemento Visible

```kotlin
@Test
fun elementoVisible_seVeEnPantalla() {
    onView(withId(R.id.text_title))
        .check(matches(isDisplayed()))
}
```

### 2. Verificar Texto

```kotlin
@Test
fun titulo_muestraTextoEsperado() {
    onView(withId(R.id.text_title))
        .check(matches(withText("Vinilos")))
        
    // Texto parcial
    onView(withId(R.id.text_description))
        .check(matches(withText(containsString("álbum"))))
}
```

### 3. Click en Elemento

```kotlin
@Test
fun clickEnBoton_navegaASiguientePantalla() {
    onView(withId(R.id.button_create)).perform(click())
    
    onView(withId(R.id.form_create_album))
        .check(matches(isDisplayed()))
}
```

### 4. RecyclerView

```kotlin
@Test
fun recyclerView_tieneElementos() {
    // Verificar mínimo de elementos
    onView(withId(R.id.recycler_albums))
        .check(matches(hasMinimumChildCount(1)))
}

@Test
fun clickEnItem_navegaADetalle() {
    // Click en primer item
    onView(withId(R.id.recycler_albums))
        .perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click()))
    
    // Verificar navegación
    onView(withId(R.id.text_album_detail_title))
        .check(matches(isDisplayed()))
}

@Test
fun scrollEnRecyclerView_muestraItem() {
    // Scroll a posición específica
    onView(withId(R.id.recycler_albums))
        .perform(RecyclerViewActions.scrollToPosition<ViewHolder>(5))
}
```

### 5. Input de Texto

```kotlin
@Test
fun ingresarTexto_guardaCorrectamente() {
    // Ingresar texto
    onView(withId(R.id.edit_album_name))
        .perform(typeText("Nuevo Álbum"), closeSoftKeyboard())
    
    // Verificar texto ingresado
    onView(withId(R.id.edit_album_name))
        .check(matches(withText("Nuevo Álbum")))
}

@Test
fun limpiarYReemplazarTexto() {
    onView(withId(R.id.edit_name))
        .perform(clearText(), typeText("Texto nuevo"), closeSoftKeyboard())
}
```

### 6. Esperar Carga (IdlingResource)

```kotlin
// util/EspressoIdlingResource.kt
object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"
    
    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)
    
    fun increment() {
        countingIdlingResource.increment()
    }
    
    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

// En Repository (antes de llamada de red)
EspressoIdlingResource.increment()
try {
    val result = api.getAlbums()
    // ...
} finally {
    EspressoIdlingResource.decrement()
}
```

## Pruebas por Historia de Usuario

### HU01 - Consultar Catálogo de Álbumes

```kotlin
@Test
fun hu01_catalogoAlbumes_muestraListaCompleta() {
    onView(withId(R.id.nav_albums)).perform(click())
    
    onView(withId(R.id.recycler_albums))
        .check(matches(isDisplayed()))
        .check(matches(hasMinimumChildCount(1)))
}

@Test
fun hu01_catalogoAlbumes_muestraImagenPortada() {
    onView(withId(R.id.nav_albums)).perform(click())
    
    // Verificar que primer item tiene imagen
    onView(withId(R.id.recycler_albums))
        .check(matches(hasDescendant(withId(R.id.image_album_cover))))
}
```

### HU02 - Detalle de Álbum

```kotlin
@Test
fun hu02_clickEnAlbum_navegaADetalle() {
    onView(withId(R.id.nav_albums)).perform(click())
    
    onView(withId(R.id.recycler_albums))
        .perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click()))
    
    onView(withId(R.id.text_album_detail_title))
        .check(matches(isDisplayed()))
}

@Test
fun hu02_detalleAlbum_muestraInformacionCompleta() {
    // Navegar a detalle
    onView(withId(R.id.nav_albums)).perform(click())
    onView(withId(R.id.recycler_albums))
        .perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click()))
    
    // Verificar campos
    onView(withId(R.id.text_album_name)).check(matches(isDisplayed()))
    onView(withId(R.id.text_album_description)).check(matches(isDisplayed()))
    onView(withId(R.id.text_album_genre)).check(matches(isDisplayed()))
}
```

### HU07 - Crear Álbum

```kotlin
@Test
fun hu07_crearAlbum_formularioVisible() {
    onView(withId(R.id.nav_albums)).perform(click())
    onView(withId(R.id.fab_create_album)).perform(click())
    
    onView(withId(R.id.form_create_album))
        .check(matches(isDisplayed()))
}

@Test
fun hu07_crearAlbum_validacionCamposRequeridos() {
    onView(withId(R.id.nav_albums)).perform(click())
    onView(withId(R.id.fab_create_album)).perform(click())
    
    // Intentar guardar sin llenar campos
    onView(withId(R.id.button_save)).perform(click())
    
    // Verificar error de validación
    onView(withId(R.id.input_layout_name))
        .check(matches(hasDescendant(withText(containsString("requerido")))))
}

@Test
fun hu07_crearAlbum_creacionExitosa() {
    onView(withId(R.id.nav_albums)).perform(click())
    onView(withId(R.id.fab_create_album)).perform(click())
    
    // Llenar formulario
    onView(withId(R.id.edit_album_name))
        .perform(typeText("Test Album"), closeSoftKeyboard())
    onView(withId(R.id.edit_album_cover))
        .perform(typeText("https://example.com/cover.jpg"), closeSoftKeyboard())
    // ... más campos
    
    // Guardar
    onView(withId(R.id.button_save)).perform(click())
    
    // Verificar regreso a lista
    onView(withId(R.id.recycler_albums))
        .check(matches(isDisplayed()))
}
```

## Comandos de Ejecución

```bash
# Ejecutar todas las pruebas de instrumentación
./gradlew connectedAndroidTest

# Ejecutar clase específica
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.tsdc.vinilos.AlbumsE2ETest

# Ejecutar método específico
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.tsdc.vinilos.AlbumsE2ETest#hu01_catalogoAlbumes_muestraListaCompleta
```

## Reporte de Defectos

Al encontrar un fallo, crear Issue con:

```markdown
## [BUG] HU01 - Descripción breve

### Pasos para reproducir
1. Abrir la app
2. Navegar a álbumes
3. ...

### Resultado esperado
Lista de álbumes visible

### Resultado actual
Pantalla en blanco

### Evidencia
[Screenshot/Log]

### Ambiente
- Dispositivo: Pixel 4a
- Android: 12
- App Version: 1.0.0
```
