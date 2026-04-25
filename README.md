# Vinilos Mobiles

Aplicación móvil nativa Android para la plataforma **Vinilos**, desarrollada en **Kotlin** como parte del curso **Ingeniería de Software para Aplicaciones Móviles** de la **Maestría en Ingeniería de Software (MISO) - Universidad de los Andes**.

## Equipo
- Juan Carlos López
- Giovanny Canasto
- Santiago Guerrero
- David Cabarcas

## Repositorio
- Código: `https://github.com/GRUPO-ANDROID-JGDS-MISW-4203/vinilos-mobiles`
- Wiki: `https://github.com/GRUPO-ANDROID-JGDS-MISW-4203/vinilos-mobiles/wiki`
- Board: `https://github.com/GRUPO-ANDROID-JGDS-MISW-4203/vinilos-mobiles/projects`

## Backend de referencia
- Repositorio: `https://github.com/TheSoftwareDesignLab/BackVynils`
- URL base: `https://backvynils-q6yc.onrender.com/`

## Stack tecnológico
- Kotlin
- Android SDK
- MVVM
- Retrofit
- Room
- Navigation Component
- LiveData

## Estrategia de ramas
- `main`: rama estable
- `develop`: integración
- `release`: preparación de entrega
- `feature/HUxx-nombre-corto`: desarrollo de funcionalidades

## MVP
El MVP del proyecto incluye las historias HU01–HU08:
- HU01 Consultar catálogo de álbumes
- HU02 Consultar detalle de álbum
- HU03 Consultar listado de artistas
- HU04 Consultar detalle de artista
- HU05 Consultar listado de coleccionistas
- HU06 Consultar detalle de coleccionista
- HU07 Crear un álbum
- HU08 Asociar tracks con un álbum

## Estado actual
Implementación en progreso de HU del MVP con foco activo en Sprint 1 (HU01 y HU02).

## Versiones técnicas
- Android Gradle Plugin: `8.2.2`
- Kotlin plugin: `1.9.22`
- Gradle Wrapper: `8.9` (definido en `gradle/wrapper/gradle-wrapper.properties`)
- Java source/target: `1.8`
- SDK Android: `compileSdk 34`, `targetSdk 34`, `minSdk 21`

## Cómo ejecutar el proyecto localmente
1. Clonar el repositorio.
2. Abrir el proyecto en Android Studio.
3. Verificar JDK 21 instalado para tooling de Android Studio.
4. Sincronizar Gradle.
5. Ejecutar la app en un emulador o dispositivo físico Android 5.0+.

## Comandos de build y calidad

```bash
gradle assembleDebug
gradle test
gradle connectedAndroidTest
gradle lint
```

## Estado de validación local (Sprint 1 - HU02)

Fecha de última validación: `2026-04-25`

- `gradle assembleDebug`: PASS
- `gradle test`: PASS
- `gradle lint`: PASS
- `gradle connectedAndroidTest`: PASS

## Documentación
La documentación del proyecto se encuentra en la wiki del repositorio.
