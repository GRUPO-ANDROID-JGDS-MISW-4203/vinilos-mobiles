# Vinilos Mobiles

Aplicacion movil nativa Android para la plataforma Vinilos, desarrollada en Kotlin para el curso de Ingenieria de Software para Aplicaciones Moviles (MISO - Uniandes).

## Equipo

- Juan Carlos Lopez
- Giovanny Canasto
- Santiago Guerrero
- David Cabarcas

## Enlaces del proyecto

- Codigo: `https://github.com/GRUPO-ANDROID-JGDS-MISW-4203/vinilos-mobiles`
- Wiki GitHub: `https://github.com/GRUPO-ANDROID-JGDS-MISW-4203/vinilos-mobiles/wiki`
- Documentacion markdown en repo: `wiki/`
- Board: `https://github.com/GRUPO-ANDROID-JGDS-MISW-4203/vinilos-mobiles/projects`
- Backend de referencia: `https://github.com/TheSoftwareDesignLab/BackVynils`
- Base URL backend esperada: `https://backvynils-q6yc.onrender.com/`

## Stack

- Kotlin + Android SDK
- MVVM + Repository + Service Adapter
- Navigation Component + ViewBinding + LiveData
- Retrofit (consumo API REST)
- JUnit + Espresso

## Versiones tecnicas requeridas

- Android Gradle Plugin: `8.2.2` (`build.gradle`)
- Gradle Wrapper: `8.9` (`gradle/wrapper/gradle-wrapper.properties`)
- Kotlin plugin: `1.9.22` (`build.gradle`)
- Java source/target compat: `1.8` (`app/build.gradle`)
- SDK Android: `compileSdk 34`, `targetSdk 34`, `minSdk 21`

## Como ejecutar localmente

1. Clonar el repositorio:
   - `git clone https://github.com/GRUPO-ANDROID-JGDS-MISW-4203/vinilos-mobiles.git`
   - `cd vinilos-mobiles`
2. Abrir el proyecto en Android Studio (version compatible con AGP 8.2.2).
3. Verificar JDK del proyecto en Android Studio (Java 8 para compilacion del modulo app).
4. Sincronizar Gradle.
5. Verificar conectividad a backend (`https://backvynils-q6yc.onrender.com/`).
6. Ejecutar en emulador o dispositivo Android 5.0+ (`minSdk 21`).

## Comandos utiles

```bash
./gradlew assembleDebug
./gradlew test
./gradlew connectedAndroidTest
./gradlew lint
```

## Estrategia de ramas (GitFlow)

- `main`: rama estable
- `develop`: integracion
- `release/*`: preparacion de entrega
- `feature/HUxx-*`: desarrollo por historia de usuario

## Historias de usuario del MVP

- HU01 Consultar catalogo de albumes
- HU02 Consultar detalle de album
- HU03 Consultar listado de artistas
- HU04 Consultar detalle de artista
- HU05 Consultar listado de coleccionistas
- HU06 Consultar detalle de coleccionista
- HU07 Crear un album
- HU08 Asociar tracks con un album

## Estrategia y evidencia de pruebas

- Estrategia documentada en `wiki/Sprint-1.md`, `wiki/Sprint-2.md`, `wiki/Sprint-3.md`.
- Pruebas automatizadas esperadas:
  - Unitarias: `app/src/test`
  - E2E (Espresso): `app/src/androidTest`
- Resultado de cada sprint debe registrarse en la seccion "Resultados de pruebas" de la Wiki.

## APK y release

- APK de validacion: generar con `./gradlew assembleDebug`.
- La entrega debe estar etiquetada en `main` con tag de release.
- Checklist minimo por release:
  - [ ] Tag creado en `main`
  - [ ] APK disponible/referenciado en artefactos del sprint
  - [ ] README y Wiki actualizados
  - [ ] Resultados de pruebas reportados

## Estado actual de implementacion (referencia de auditoria)

- Hay ramas remotas con avances HU01/HU02:
  - `origin/FEAT_HU01_Albumes`
  - `origin/FEAT_HU02_DetalleAlbum`
  - `origin/feature/HU02-detalle-album`
- La trazabilidad detallada se documenta en `wiki/Sprint-1.md`.
