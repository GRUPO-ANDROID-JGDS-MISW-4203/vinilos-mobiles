# Vinilos Mobile - Guía para Agentes AI

## Contexto del Proyecto

**Vinilos** es una aplicación móvil Android nativa desarrollada en **Kotlin** para el curso de Ingeniería de Software para Aplicaciones Móviles de la Maestría en Ingeniería de Software (MISO) de la Universidad de los Andes.

### Datos del Proyecto
- **Cliente**: The Software Design Company (TSDC)
- **Backend**: https://github.com/TheSoftwareDesignLab/BackVynils
- **Equipo**: 4 desarrolladores
- **Duración**: 8 semanas (1 inception + 3 sprints de desarrollo)

### Stack Tecnológico
- **Lenguaje**: Kotlin
- **IDE**: Android Studio
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Patrones**: Repository, Service Adapter, DAO
- **Librerías**: Jetpack (LiveData, ViewModel, Room, Navigation), Retrofit, Material Design
- **Pruebas**: Espresso (E2E), JUnit (unitarias)
- **Control de versiones**: Git con GitFlow

---

## Historias de Usuario del MVP (HU01-HU08)

| ID | Historia | Rol | Sprint |
|----|----------|-----|--------|
| HU01 | Consultar catálogo de álbumes | Visitante | 1 |
| HU02 | Consultar detalle de álbum | Visitante | 1 |
| HU03 | Consultar listado de artistas | Visitante | 2 |
| HU04 | Consultar detalle de artista | Visitante | 2 |
| HU05 | Consultar listado de coleccionistas | Visitante | 2 |
| HU06 | Consultar detalle de coleccionista | Visitante | 3 |
| HU07 | Crear un álbum | Coleccionista | 3 |
| HU08 | Asociar tracks con un álbum | Coleccionista | 3 |

---

## Arquitectura MVVM

```
┌─────────────────────────────────────────────────────────────┐
│                         VIEW                                 │
│  (Activity / Fragment + XML Layout + ViewBinding)           │
│                           │                                  │
│                     observes LiveData                        │
│                           ▼                                  │
├─────────────────────────────────────────────────────────────┤
│                      VIEW MODEL                              │
│  (ViewModel + LiveData + transformaciones)                  │
│                           │                                  │
│                      invoca métodos                          │
│                           ▼                                  │
├─────────────────────────────────────────────────────────────┤
│                      REPOSITORY                              │
│  (Orquesta acceso a datos: local vs remoto)                 │
│                     /           \                            │
│                    ▼             ▼                           │
│            ┌──────────┐    ┌──────────────┐                 │
│            │   DAO    │    │   Service    │                 │
│            │  (Room)  │    │   Adapter    │                 │
│            └──────────┘    │  (Retrofit)  │                 │
│                            └──────────────┘                 │
├─────────────────────────────────────────────────────────────┤
│                        MODEL                                 │
│  (Data classes / Entities)                                  │
└─────────────────────────────────────────────────────────────┘
```

---

## Estructura de Paquetes Recomendada

```
com.tsdc.vinilos/
├── data/
│   ├── local/
│   │   ├── dao/           # DAOs de Room
│   │   ├── entity/        # Entidades de base de datos
│   │   └── database/      # Configuración de Room
│   ├── remote/
│   │   ├── api/           # Interfaces Retrofit
│   │   ├── dto/           # Data Transfer Objects
│   │   └── adapter/       # Service Adapters
│   └── repository/        # Repositorios
├── domain/
│   └── model/             # Modelos de dominio
├── ui/
│   ├── albums/            # Fragmentos y ViewModels de álbumes
│   ├── artists/           # Fragmentos y ViewModels de artistas
│   ├── collectors/        # Fragmentos y ViewModels de coleccionistas
│   └── common/            # Componentes UI compartidos
├── di/                    # Inyección de dependencias (opcional)
└── util/                  # Utilidades y extensiones
```

---

## Reglas de Desarrollo

### Código Kotlin
1. Usar `data class` para modelos
2. Usar `sealed class` para estados de UI
3. Preferir `val` sobre `var`
4. Usar extensiones de Kotlin para código limpio
5. Manejar nullability con operadores seguros (`?.`, `?:`, `!!`)

### MVVM
1. ViewModels NO deben tener referencias a Views
2. Usar LiveData para comunicación View ↔ ViewModel
3. Repositorios manejan la lógica de caché
4. Service Adapters encapsulan llamadas Retrofit

### Pruebas
1. Cada HU debe tener pruebas E2E en Espresso
2. Usar `@Before` para setup
3. Usar `IdlingResource` para operaciones asíncronas
4. Reportar defectos como Issues en GitHub

### GitFlow
1. `main`: código estable (releases)
2. `develop`: integración continua
3. `feature/HUxx-descripcion`: desarrollo de HUs
4. PRs con "squash and merge"
5. Code review obligatorio

---

## Comandos Útiles

```bash
# Ejecutar pruebas unitarias
./gradlew test

# Ejecutar pruebas de instrumentación (Espresso)
./gradlew connectedAndroidTest

# Generar APK de debug
./gradlew assembleDebug

# Analizar código con Lint
./gradlew lint

# Limpiar y reconstruir
./gradlew clean build
```

---

## API REST Backend

Base URL: `https://backvynils-q6yc.onrender.com` (o localhost para desarrollo)

### Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/albums` | Listar álbumes |
| GET | `/albums/{id}` | Detalle de álbum |
| POST | `/albums` | Crear álbum |
| POST | `/albums/{id}/tracks` | Agregar track |
| GET | `/musicians` | Listar artistas |
| GET | `/musicians/{id}` | Detalle de artista |
| GET | `/collectors` | Listar coleccionistas |
| GET | `/collectors/{id}` | Detalle de coleccionista |

---

## Criterios de Calidad

### Código
- [ ] Sigue arquitectura MVVM
- [ ] Sin código duplicado
- [ ] Nombres descriptivos
- [ ] Sin warnings de Lint

### Funcionalidad
- [ ] HU implementada completamente
- [ ] Manejo de errores de red
- [ ] Estados de carga visibles
- [ ] Funciona en Android 5.0+

### Pruebas
- [ ] Pruebas E2E para cada HU
- [ ] Escenarios positivos y negativos
- [ ] Pruebas pasan en CI

### Documentación
- [ ] README actualizado
- [ ] Wiki con diseño arquitectónico
- [ ] Comentarios en código complejo

---

## Referencias

- [Guía de Arquitectura Android](https://developer.android.com/jetpack/guide)
- [Kotlin para Android](https://developer.android.com/kotlin)
- [Espresso Testing](https://developer.android.com/training/testing/espresso)
- [Material Design](https://material.io/design)
- [GitFlow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)
