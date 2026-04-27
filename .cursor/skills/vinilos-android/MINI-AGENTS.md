# Mini-Agentes para Tareas Específicas

## Agent: Implementar Historia de Usuario

### Trigger
Cuando el usuario dice: "Implementar HU0X", "Crear funcionalidad de...", "Desarrollar historia..."

### Proceso

```markdown
## Checklist de Implementación HU0X

### 1. Análisis (5 min)
- [ ] Leer criterios de aceptación
- [ ] Identificar componentes necesarios
- [ ] Verificar endpoint de API disponible

### 2. Backend/Data Layer (30 min)
- [ ] Crear DTO en `data/remote/dto/`
- [ ] Crear modelo de dominio en `domain/model/`
- [ ] Agregar endpoint en `VinilosApi`
- [ ] Crear ServiceAdapter
- [ ] Crear/actualizar Repository

### 3. UI Layer (45 min)
- [ ] Crear Fragment en `ui/[modulo]/`
- [ ] Crear ViewModel
- [ ] Crear layout XML
- [ ] Crear Adapter (si es lista)
- [ ] Agregar navegación en nav_graph.xml
- [ ] Agregar item en bottom_nav (si aplica)

### 4. Pruebas (30 min)
- [ ] Crear archivo de pruebas E2E
- [ ] Implementar escenarios positivos
- [ ] Implementar escenarios de error
- [ ] Ejecutar pruebas localmente

### 5. Integración (15 min)
- [ ] Verificar Lint sin warnings
- [ ] Commit con mensaje convencional
- [ ] Push a feature branch
- [ ] Crear PR hacia develop
```

---

## Agent: Crear Prueba E2E

### Trigger
Cuando el usuario dice: "Crear prueba para...", "Test E2E de...", "Prueba Espresso..."

### Template

```kotlin
@RunWith(AndroidJUnit4::class)
@LargeTest
class {NombreHU}E2ETest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun {nombreHU}_escenarioPrincipal_resultadoEsperado() {
        // Given - Estado inicial
        
        // When - Acción del usuario
        onView(withId(R.id.nav_{modulo})).perform(click())
        
        // Then - Verificación
        onView(withId(R.id.recycler_{modulo}))
            .check(matches(isDisplayed()))
    }

    @Test
    fun {nombreHU}_escenarioError_muestraMensaje() {
        // TODO: Implementar escenario de error
    }
}
```

---

## Agent: Crear ViewModel

### Trigger
Cuando el usuario dice: "Crear ViewModel para...", "ViewModel de..."

### Template

```kotlin
class {Nombre}ViewModel(
    private val repository: {Nombre}Repository
) : ViewModel() {

    private val _state = MutableLiveData<UIState<{TipoDato}>>()
    val state: LiveData<UIState<{TipoDato}>> = _state

    fun load{Datos}() {
        viewModelScope.launch {
            _state.value = UIState.Loading
            try {
                val data = repository.get{Datos}()
                _state.value = UIState.Success(data)
            } catch (e: Exception) {
                _state.value = UIState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

class {Nombre}ViewModelFactory(
    private val repository: {Nombre}Repository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return {Nombre}ViewModel(repository) as T
    }
}
```

---

## Agent: Crear Fragment

### Trigger
Cuando el usuario dice: "Crear Fragment para...", "Vista de..."

### Template

```kotlin
class {Nombre}Fragment : Fragment() {

    private var _binding: Fragment{Nombre}Binding? = null
    private val binding get() = _binding!!
    
    private val viewModel: {Nombre}ViewModel by viewModels {
        {Nombre}ViewModelFactory({Nombre}Repository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment{Nombre}Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeState()
        viewModel.load{Datos}()
    }

    private fun setupUI() {
        // Configurar RecyclerView, listeners, etc.
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> showLoading()
                is UIState.Success -> show{Datos}(state.data)
                is UIState.Error -> showError(state.message)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recycler{Nombre}.visibility = View.GONE
    }

    private fun show{Datos}(data: {TipoDato}) {
        binding.progressBar.visibility = View.GONE
        binding.recycler{Nombre}.visibility = View.VISIBLE
        // adapter.submitList(data)
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

---

## Agent: Code Review

### Trigger
Cuando el usuario dice: "Revisar código", "Code review de...", "PR review"

### Checklist

```markdown
## Code Review Checklist

### Arquitectura MVVM
- [ ] ViewModel no tiene referencias a Views
- [ ] Repository maneja acceso a datos
- [ ] ServiceAdapter encapsula Retrofit
- [ ] Fragment solo tiene lógica de UI

### Kotlin
- [ ] Uso de data class para modelos
- [ ] Manejo correcto de nullability
- [ ] Uso de coroutines para async
- [ ] Sin código duplicado

### UI/UX
- [ ] Layouts usan Material Design
- [ ] IDs siguen convención (tipo_descripcion)
- [ ] Strings externalizados
- [ ] ContentDescription en imágenes

### Pruebas
- [ ] Pruebas E2E implementadas
- [ ] Escenarios positivos y negativos
- [ ] Pruebas pasan localmente

### Git
- [ ] Commits siguen convención
- [ ] PR referencia Issue/HU
- [ ] Branch name correcto
```

---

## Agent: Debug Error

### Trigger
Cuando el usuario dice: "Error en...", "No funciona...", "Crash en..."

### Proceso

```markdown
## Debugging Checklist

### 1. Identificar el Error
- [ ] Leer stack trace completo
- [ ] Identificar archivo y línea
- [ ] Reproducir el error

### 2. Errores Comunes

#### NullPointerException
- Verificar inicialización de variables
- Usar operadores seguros (?., ?:)
- Verificar binding en onDestroyView

#### NetworkException
- Verificar permisos INTERNET en Manifest
- Verificar URL del endpoint
- Verificar conectividad

#### RecyclerView vacío
- Verificar adapter.submitList()
- Verificar layout manager
- Verificar datos del ViewModel

#### Crash en rotación
- ViewModel debe sobrevivir rotación
- Verificar savedInstanceState
- No guardar referencias a Views

### 3. Herramientas
- Logcat con filtro por tag
- Breakpoints en Android Studio
- Layout Inspector para UI
- Network Profiler para REST
```

---

## Agent: Crear Release

### Trigger
Cuando el usuario dice: "Crear release", "Preparar entrega", "Generar APK"

### Proceso

```bash
# 1. Verificar todo está listo
./gradlew lint
./gradlew test
./gradlew connectedAndroidTest

# 2. Crear rama de release
git checkout develop
git pull origin develop
git checkout -b release/sprint-X

# 3. Actualizar versión
# Editar app/build.gradle: versionName, versionCode
git commit -m "chore: bump version to X.Y.Z"

# 4. Generar APK
./gradlew assembleRelease
# APK en app/build/outputs/apk/release/

# 5. Merge a main
git checkout main
git merge release/sprint-X --no-ff
git tag -a vX.Y.Z -m "Sprint X Release"
git push origin main --tags

# 6. Merge a develop
git checkout develop
git merge release/sprint-X --no-ff
git push origin develop

# 7. Subir APK al repositorio
cp app/build/outputs/apk/release/app-release.apk releases/vinilos-vX.Y.Z.apk
git add releases/
git commit -m "chore: add release APK vX.Y.Z"
git push origin main
```
