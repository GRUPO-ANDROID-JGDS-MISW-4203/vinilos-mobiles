# AGENTS.md

## Proposito

Esta guia define como deben trabajar Codex, Cursor y cualquier otro asistente AI dentro de `vinilos-mobiles`.
Su objetivo es alinear decisiones de implementacion, documentacion, pruebas y entrega con:

- el estado real del repositorio,
- el marco teorico del curso,
- la arquitectura esperada para Android nativo,
- y los entregables academicos del proyecto.

Este archivo es la guia operativa principal del repositorio.

## Estado real del repositorio

El agente debe partir del estado real, no del estado ideal:

- Proyecto Android nativo en Kotlin con un solo modulo `app/`.
- Uso actual de XML Views, `Fragment`, `Navigation Component`, `ViewBinding`, `LiveData`, `ViewModel`, `Retrofit`, `Glide` y corrutinas.
- Existe implementacion funcional de UI para albumes, artistas y coleccionistas.
- La arquitectura esta orientada a MVVM, pero no toda la app esta uniformemente consolidada.
- Existen pruebas instrumentadas en `app/src/androidTest`.
- Existe `repository/` y `network/`, pero la cobertura por feature es desigual.
- No existe hoy carpeta `docs/`.
- No existe hoy carpeta `./.codex/`.
- No existe hoy carpeta `./.cursor/`.
- No existe hoy una persistencia productiva basada en `Room` o `DAO` en codigo principal.
- El lenguaje configurado en Gradle del modulo `app` sigue siendo Java/Kotlin 1.8, aunque el marco academico del usuario prefiera lineas mas modernas.

El agente no debe afirmar que el repositorio ya implementa algo que aun no existe en codigo.

## Orden de consulta obligatorio

Antes de proponer cambios o editar codigo, use este orden:

1. `AGENTS.md`
2. `README.md`
3. `memory/README.md`
4. `memory/rules/`
5. `memory/policies/`
6. `memory/skills/`
7. `memory/knowledge/`
8. Codigo real en `app/src/main`
9. Pruebas reales en `app/src/test` y `app/src/androidTest`
10. `docs/`, `./.codex/` y `./.cursor/` solo si llegan a existir

Si una regla documental contradice el codigo real:

- el codigo real tiene prioridad como fuente tecnica actual,
- la desviacion debe ser reportada,
- y la documentacion debe corregirse si hace parte de la tarea o del diff razonable.

## Mapa operativo para LLMs

### Codex

Codex debe usar:

- `AGENTS.md` como contrato operativo principal,
- `README.md` como guia de stack y flujo de ejecucion,
- `memory/` como base documental viva del proyecto,
- y el codigo real como fuente final de verdad tecnica.

### Cursor

Cursor debe usar:

- `AGENTS.md` como marco comun,
- `memory/rules/` para restricciones de implementacion,
- `memory/policies/` para definition of done y calidad,
- `memory/skills/` para patrones de solucion y checklists,
- `memory/knowledge/` para contexto academico, funcional y arquitectonico.

### Otros asistentes

Cualquier otro agente debe respetar la misma jerarquia:

- `AGENTS.md`
- `README.md`
- `memory/`
- codigo y pruebas reales

Ningun asistente debe inventar capas, carpetas o componentes como si ya existieran.

## Base documental compartida

La carpeta `memory/` es la base comun para asistentes AI en este repositorio.

```text
/memory
  README.md
  /skills
  /rules
  /archive
  /policies
  /knowledge
```

### Uso de cada carpeta

- `memory/skills/`: playbooks de implementacion y validacion por tema tecnico.
- `memory/rules/`: reglas concretas y accionables para tocar el repositorio.
- `memory/archive/`: snapshots, historico y material que no debe gobernar el presente.
- `memory/policies/`: criterios obligatorios de calidad, seguridad, entrega y done.
- `memory/knowledge/`: contexto duradero del curso, proyecto, patrones y estado del repo.

### Regla de mantenimiento documental

- `skills/` explica como hacer.
- `rules/` define que se puede y no se puede hacer.
- `policies/` define que debe cumplirse para aceptar el trabajo.
- `knowledge/` explica por que existe esa linea de trabajo.
- `archive/` conserva historia, no decisiones vigentes.

## Requisitos del proyecto

El agente debe trabajar bajo estos requisitos funcionales y academicos:

- La app es Android nativa en Kotlin.
- El MVP objetivo cubre HU01-HU08.
- El backend de referencia es `BackVynils`.
- El trabajo se evalua por iteraciones, no solo por codigo final.
- La wiki, el README, la estrategia de pruebas y la evidencia de ejecucion son parte del producto.
- La app debe operar en Android Lollipop o superior segun el marco del curso.
- La arquitectura esperada es MVVM con patrones Repository y Service Adapter.
- Cada historia debe quedar trazable a codigo, pruebas y documentacion.
- La calidad percibida en UI, manejo de errores y navegacion es parte del alcance.

## Estado funcional esperado por roadmap

### Inception

Debe existir:

- backlog HU01-HU08,
- planeacion por sprint,
- prototipo UX/UI navegable,
- reglas de equipo,
- wiki organizada,
- y configuracion basica de GitFlow.

### Sprint 1

Debe cubrir:

- HU01 consultar catalogo de albumes,
- HU02 consultar detalle de album,
- pruebas automatizadas iniciales,
- arquitectura MVVM trazable para estas historias.

### Sprint 2

Debe cubrir:

- HU03 listado de artistas,
- HU04 detalle de artista,
- HU05 listado de coleccionistas.

### Sprint 3

Debe cubrir:

- HU06 detalle de coleccionista,
- HU07 crear album,
- HU08 asociar tracks con album.

## Estado tecnico actual resumido

Con base en el codigo actual, el agente debe asumir:

- existe navegacion y layouts para HU01-HU08,
- hay presencia de fragments por dominio,
- hay una capa `viewmodel`, pero no toda la app esta simetricamente modelada,
- `AlbumRepository` y `VinylsApiService` son referencias de estilo existentes,
- las pruebas Android actuales se concentran en albumes y artistas,
- hay espacio para consolidar reglas de arquitectura, pruebas y consistencia entre features.

## Arquitectura objetivo a preservar

La linea base del proyecto es Android nativo con enfoque MVVM.

### Capas esperadas

- `ui/`: `Activity`, `Fragment`, adapters, binding, render de estado y navegacion.
- `viewmodel/`: coordinacion de casos de uso de UI y exposicion de estado observable.
- `repository/`: orquestacion de fuentes de datos y abstraccion de acceso.
- `network/`: contratos Retrofit, configuracion HTTP y adaptadores de servicio.
- `model/`: modelos de dominio, request/response models y estructuras compartidas.

### Restricciones arquitectonicas

- No poner logica de negocio en `Fragment`, `Activity` o adapters.
- No hacer llamadas Retrofit directas desde UI.
- No usar `Context` de vista como dependencia permanente del `ViewModel`.
- No acoplar parsing de red con render de componentes visuales.
- No mezclar navegacion con logica de datos mas alla del disparo de acciones.
- Si se introduce cache o persistencia local, debe entrar por `repository/`.
- Si se introduce Room, DAO o almacenamiento, debe mantenerse desacoplado de la UI.

### Reglas para MVVM

- La View observa estado, no decide reglas de negocio.
- El ViewModel expone estado y coordina operaciones.
- El Repository encapsula obtencion y escritura de datos.
- El Service Adapter encapsula detalles HTTP.
- Los modelos de UI no deben contaminarse con dependencias Android si no es necesario.

## Principios de diseno obligatorios

### SOLID

- `S`: una clase debe tener una responsabilidad clara.
- `O`: preferir extensiones por composicion antes que modificar flujos sin necesidad.
- `L`: subtipos no deben romper contratos esperados.
- `I`: no crear interfaces infladas ni genricas sin un uso real.
- `D`: dependencias de alto nivel no deben quedar amarradas a implementaciones concretas de red o UI.

### GRASP

- Information Expert: la logica debe vivir donde esta la informacion necesaria.
- Creator: quien compone o administra datos relacionados puede crear el objeto colaborador.
- Controller: `ViewModel` o `Repository`, no `Fragment`, deben coordinar flujos.
- Low Coupling: evitar que UI conozca detalles de transporte o parseo.
- High Cohesion: separar responsabilidades por feature y capa.

### GoF aplicable al repo

- Observer: `LiveData` y observacion lifecycle-aware.
- Adapter: `Service Adapter` y mapeo API -> modelo de app.
- Factory: helpers o builders para creacion de objetos complejos cuando aparezcan.
- Singleton: solo para componentes realmente globales y controlados; evitar abuso.
- Strategy: util para validaciones, cache o politicas de transformacion si emerge complejidad.

## Reglas de UI y experiencia movil

- La UI debe respetar limitaciones de pantalla, ciclo de vida y navegacion Android.
- No bloquear el hilo principal con parsing, IO o red.
- Mostrar estados de carga cuando la operacion no sea inmediata.
- Mostrar errores claros y recuperables cuando falle la red o la validacion.
- Las cadenas visibles deben salir de `strings.xml`, no ir hardcodeadas en layouts o Kotlin.
- Las imagenes remotas deben cargarse con la libreria ya presente, no con soluciones manuales.
- Los componentes interactivos deben ser testeables y estables para Espresso.
- Si una pantalla cambia, revisar impacto en adapters, nav graph, menu, toolbar y pruebas.

## Reglas de Kotlin y Android

- Preferir `val` sobre `var`.
- Usar nullability de forma explicita, sin abusar de `!!`.
- Preferir funciones pequenas, expresivas y con nombres orientados al dominio.
- Evitar clases god y fragments gigantes.
- Reutilizar extensiones, helpers o mappers cuando reduzcan duplicacion real.
- Evitar imports wildcard.
- Evitar dependencias circulares entre paquetes.
- Respetar el ciclo de vida de fragments y bindings para prevenir leaks.
- No retener referencias a vistas fuera de su ciclo de vida.

## Reglas de red, asincronia y desempeno

- Toda llamada remota debe quedar fuera del hilo principal.
- Las operaciones asincronas deben ser cancelables o lifecycle-aware cuando aplique.
- El manejo de errores de red debe contemplar fallo HTTP, timeout y respuesta inesperada.
- No duplicar llamadas de red si la misma pantalla ya tiene estado utilizable.
- Si se agrega cache, documentar alcance, invalidez y criterio de refresco.
- Evitar crear objetos o transformaciones costosas de forma innecesaria en adapters o bindings.

## Reglas de pruebas

La estrategia de calidad del curso exige combinacion de verificacion y validacion.

### Minimo esperado

- Compilacion correcta.
- Pruebas locales relevantes para el cambio.
- Pruebas instrumentadas para flujos UI cuando el cambio afecte navegacion o pantallas.
- Evidencia clara cuando no sea posible ejecutar una prueba.

### Linea de pruebas por nivel

- Unitarias: validaciones, mappers, logica de `ViewModel`, decisiones de `Repository`.
- Instrumentadas: navegacion, render de datos, estados de carga, estados de error.
- Integracion con `MockWebServer`: consumo HTTP sin depender del backend real.
- Exploratorias o manuales: cuando una historia lo requiera y se deba dejar evidencia.

### Herramientas a preferir

- `JUnit`
- `Espresso`
- `MockWebServer`
- `ADB` para apoyo diagnostico
- `Firebase Test Lab` si la tarea exige validacion en fragmentacion de dispositivos

### Reglas de testabilidad

- No romper ids, textos o estructura necesaria para pruebas sin revisar tests.
- Si la UI cambia, actualizar pruebas o documentar por que no aplica.
- Los flujos felices y de error deben estar cubiertos en features importantes.

### Comandos base esperados

En Windows PowerShell:

```powershell
.\gradlew assembleDebug
.\gradlew test
.\gradlew connectedAndroidTest
.\gradlew lint
```

## Reglas de documentacion

- Documentar estado real, no arquitectura idealizada.
- Si cambia una convencion reusable, moverla a `memory/`.
- Si se crea `docs/`, `./.codex/` o `./.cursor/`, deben alinearse con esta base.
- No usar documentacion para esconder deuda tecnica o gaps de implementacion.
- Si una historia cambia de alcance, reflejarlo en `memory/knowledge/project-status.md` o documentos equivalentes.

## Reglas de Git y colaboracion

- Preservar la estructura actual salvo solicitud funcional clara.
- Preferir diffs pequenos y reversibles.
- No reestructurar paquetes por gusto.
- Todo cambio debe ser compatible con GitFlow definido por el proyecto.
- No tocar artefactos, versiones o configuracion global sin justificar el impacto.
- No hardcodear secretos, tokens o credenciales.

## Criterios de calidad obligatorios

Para considerar un cambio aceptable:

- la app debe compilar,
- la navegacion no debe quedar rota,
- los estados de carga y error deben seguir siendo coherentes,
- el cambio debe respetar MVVM y separacion de responsabilidades,
- el codigo no debe introducir deuda evitable obvia,
- y la documentacion debe seguir alineada con el estado real.

## Entregables esperados por iteracion

Cada iteracion, excepto inception, debe poder dejar:

- codigo funcional,
- estrategia de pruebas,
- artefactos de prueba,
- README util para correr el proyecto,
- APK o evidencia de build,
- y documentacion de arquitectura/diseno actualizada.

El agente debe recordar que en este curso la evidencia de ejecucion y la trazabilidad son parte del entregable, no solo el codigo.

## Que hacer si faltan carpetas de soporte

Como hoy no existen `docs/`, `./.codex/` ni `./.cursor/`, cualquier agente debe:

- usar `memory/` como base compartida,
- no bloquearse esperando esas carpetas,
- y no afirmar que fueron consultadas si no existen.

## Politica de evolucion

Si el repositorio crece, la prioridad es:

1. mantener coherencia con el codigo real,
2. consolidar conocimiento reusable en `memory/`,
3. estandarizar reglas y politicas antes de seguir agregando excepciones,
4. y solo despues especializar configuraciones por herramienta.
