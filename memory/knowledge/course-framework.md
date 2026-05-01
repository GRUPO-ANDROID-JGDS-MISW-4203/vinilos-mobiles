# Marco del Curso y del Proyecto

## Producto

`Vinilos` es una aplicacion movil Android nativa construida en Kotlin para el curso de Ingenieria de Software para Aplicaciones Moviles de MISO Uniandes.

## Restricciones academicas y tecnicas

- Android nativo, no web ni hibrido.
- Kotlin como lenguaje principal.
- Trabajo iterativo de 8 semanas.
- Inception + 3 sprints de desarrollo.
- MVP de 8 historias HU01-HU08.
- GitFlow, wiki, tablero, milestones, evidencias y pruebas como parte del entregable.

## Stack esperado por curso

- Kotlin
- Android SDK
- MVVM
- Repository
- Service Adapter
- Retrofit
- Room o persistencia local cuando el alcance lo requiera
- Navigation Component
- LiveData o componentes lifecycle-aware
- Espresso para E2E base

## Calidad esperada

- manejo de errores de red,
- estados de carga visibles,
- trazabilidad de historias,
- documentacion por iteracion,
- README ejecutable,
- APK o evidencia de build,
- pruebas y artefactos asociados.

## Vision de arquitectura

La arquitectura promovida por el curso separa:

- vista,
- view model,
- acceso a datos,
- modelos,
- y adaptadores a infraestructura.

La separacion no es ornamental: busca mantenibilidad, testabilidad y control del ciclo de vida.
