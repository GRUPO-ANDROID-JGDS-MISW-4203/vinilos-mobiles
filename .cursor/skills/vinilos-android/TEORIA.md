# Teoría del Curso - Ingeniería de Software para Apps Móviles

## Ecosistema Móvil

### Plataformas Dominantes
- **Android**: ~70% del mercado global (Google)
- **iOS**: ~27% del mercado global (Apple)
- **HarmonyOS**: Emergente (Huawei)

### Mercados de Aplicaciones
| Mercado | Plataforma | Apps (2020) |
|---------|------------|-------------|
| Google Play | Android | 2.87M |
| App Store | iOS | 1.96M |
| AppGallery | Huawei | 420K |

### Importancia del Ecosistema
- Distribución global a bajo costo
- Analítica de datos operacionales
- Feedback de usuarios (calificaciones, comentarios)
- Gestión de requerimientos basada en reviews

---

## Tipos de Aplicaciones Móviles

### 1. Apps Nativas
- **Lenguajes**: Kotlin/Java (Android), Swift/Objective-C (iOS)
- **Pros**: Mejor rendimiento, acceso completo a hardware
- **Contras**: Costo de desarrollo para cada plataforma

### 2. Apps Web para Móvil
- **Tecnologías**: HTML, CSS, JavaScript
- **Pros**: Un solo desarrollo, sin instalación
- **Contras**: Sin acceso a hardware, requiere conexión

### 3. Progressive Web Apps (PWA)
- **Características**: Service Workers, manifest
- **Pros**: Funcionalidad offline, icono en home
- **Contras**: Acceso limitado a hardware

### 4. Apps Híbridas
- **Frameworks**: Cordova, Ionic, PhoneGap
- **Características**: WebView + cascarón nativo
- **Pros**: Un código para múltiples plataformas
- **Contras**: Rendimiento inferior a nativas

### 5. Cross-Platform Nativas
- **Frameworks**: Flutter (Dart), React Native (JS), Kotlin Multiplatform
- **Características**: Un código, compilación a nativo
- **Pros**: Rendimiento cercano a nativo, un equipo
- **Contras**: Curva de aprendizaje del framework

---

## Lenguajes de Programación

### Kotlin (Android - Oficial desde 2019)
- Multiparadigma (OOP + Funcional)
- Interoperable con Java
- Null safety
- Coroutines para asincronía
- Data classes, sealed classes

### Swift (iOS - Desde 2014)
- Multiparadigma
- Compilación AOT
- Optionals para null safety
- Closures, protocols

### Dart (Flutter)
- Compilación AOT a nativo
- Mixins (inspirado en Smalltalk)
- Isolates (inspirado en Erlang)
- Null safety

---

## Sistemas de Diseño

### Evolución Histórica
1. **Skeuomorphism** (Realismo): Emula objetos físicos
2. **Flat Design**: Minimalista, formas geométricas
3. **Material Design** (Google): Metáfora de papel/tarjetas
4. **Fluent Design** (Microsoft): Metáfora de acrílico
5. **Neumorphism**: Efecto de relieve suave

### Material Design (Android)
- Componentes: Cards, FAB, BottomNav, AppBar
- Colores: Primary, Secondary, Surface, Error
- Tipografía: Roboto
- Elevación y sombras

---

## Componentes Esenciales Android

### 1. Activity
- Representa una pantalla
- Ciclo de vida: onCreate → onStart → onResume → onPause → onStop → onDestroy
- Una app puede tener múltiples Activities

### 2. Fragment
- Porción reutilizable de UI
- Ciclo de vida similar a Activity
- Se aloja dentro de una Activity
- Mejor para navegación modular

### 3. Service
- Operaciones en background
- Sin interfaz gráfica
- Tipos: Started, Bound, Foreground

### 4. Content Provider
- Compartir datos entre apps
- URI para acceso
- Ejemplo: Contactos, Galería

### 5. Broadcast Receiver
- Responde a eventos del sistema
- Ejemplos: Batería baja, SMS recibido

---

## Arquitectura MVVM

### Capas

```
┌─────────────────────────────────────────┐
│              VIEW                        │
│  (Activity/Fragment + ViewBinding)       │
│                                          │
│  Responsabilidad:                        │
│  - Renderizar UI                         │
│  - Capturar eventos de usuario           │
│  - Observar LiveData                     │
└─────────────────────────────────────────┘
                    │
                    │ observes / calls
                    ▼
┌─────────────────────────────────────────┐
│            VIEW MODEL                    │
│  (ViewModel + LiveData)                  │
│                                          │
│  Responsabilidad:                        │
│  - Preparar datos para UI                │
│  - Manejar lógica de presentación        │
│  - Sobrevivir rotaciones                 │
└─────────────────────────────────────────┘
                    │
                    │ calls
                    ▼
┌─────────────────────────────────────────┐
│            REPOSITORY                    │
│  (Single source of truth)                │
│                                          │
│  Responsabilidad:                        │
│  - Orquestar fuentes de datos            │
│  - Implementar estrategias de caché      │
│  - Abstraer origen de datos              │
└─────────────────────────────────────────┘
          │                    │
          ▼                    ▼
┌──────────────────┐  ┌──────────────────┐
│      DAO         │  │  ServiceAdapter  │
│    (Room)        │  │   (Retrofit)     │
│                  │  │                  │
│  Local Data      │  │  Remote Data     │
└──────────────────┘  └──────────────────┘
```

### Patrones Asociados
- **Repository**: Fachada para acceso a datos
- **Service Adapter**: Encapsula llamadas REST
- **DAO**: Data Access Object para BD local
- **Observer**: LiveData implementa este patrón

---

## Pruebas en Android

### Pirámide de Pruebas
```
         /\
        /  \      UI Tests (Espresso)
       /----\     10%
      /      \
     /--------\   Integration Tests
    /          \  20%
   /------------\
  /              \ Unit Tests (JUnit)
 /----------------\ 70%
```

### Tipos de Pruebas

| Tipo | Herramienta | Ubicación |
|------|-------------|-----------|
| Unitarias | JUnit, MockK | `test/` |
| Integración | JUnit, Robolectric | `test/` |
| E2E/UI | Espresso | `androidTest/` |
| Monkey | adb monkey | CLI |
| Rippers | Firebase Test Lab | Cloud |

### Análisis Estático
- **Lint**: Detecta errores potenciales
- **Detekt**: Análisis de código Kotlin
- **SonarQube**: Métricas de calidad

---

## GitFlow

### Ramas
- `main`: Código en producción
- `develop`: Integración continua
- `feature/*`: Desarrollo de funcionalidades
- `release/*`: Preparación de releases
- `hotfix/*`: Correcciones urgentes

### Proceso
1. Crear feature branch desde develop
2. Desarrollar con commits atómicos
3. Crear PR hacia develop
4. Code review + aprobación
5. Squash and merge
6. Delete feature branch

---

## Calendario del Proyecto

| Semana | Fase | Entregables |
|--------|------|-------------|
| 1-2 | Inception | Backlog, UX/UI, Repo |
| 3-4 | Sprint 1 | HU01, HU02, Pruebas |
| 5-6 | Sprint 2 | HU03, HU04, HU05 |
| 7-8 | Sprint 3 | HU06, HU07, HU08 |

### Por Sprint
- Estrategia de pruebas
- Diseño arquitectónico
- Código + pruebas
- APK
- Documentación en Wiki
- Retrospectiva
