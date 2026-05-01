# Semana 5: Desempeno, Concurrencia y Testing Avanzado

## Enfoque de la semana

La semana 5 agrega cuatro frentes que no deben quedar difusos:

- pruebas automatizadas con mejor criterio,
- concurrencia para evitar ANRs,
- caching como tecnica de rendimiento y soporte offline parcial,
- pruebas cloud para enfrentar fragmentacion.

## Pruebas

- Distinguir manual, automatizada y automatica.
- Combinar tecnicas, niveles y tipos de prueba.
- Evitar depender solo de pruebas manuales o solo de interfaz.
- Apuntar a una distribucion cercana a piramide, no al anti patron del cono de helado.

## Concurrencia y corrutinas

- Android parte de un hilo principal sensible a bloqueos.
- Las tareas largas deben salir de `Main`.
- Kotlin ofrece scopes, dispatchers y builders para hacerlo de forma lifecycle-aware.

## Caching

- El cache reduce latencia, red y CPU si se usa con criterio.
- El cache consume memoria o disco, por lo que necesita limites y politica.
- Tecnicas relevantes:
  - `LruCache`
  - `ArrayMap`
  - `SparseArray`
  - `SharedPreferences`
  - cache de imagenes con Glide
  - cache HTTP o local cuando la arquitectura lo justifique

## Cloud testing y fragmentacion

- Un solo emulador no representa el ecosistema Android.
- ADB sirve para control local y extraccion de evidencia.
- Firebase Test Lab sirve para Robo Tests, instrumentacion y matrices de dispositivos.

## Implicacion para Vinilos

Para iteracion 2 no basta con implementar historias.
Tambien se espera:

- micro-optimizaciones,
- evidencia de buenas practicas de memoria,
- uso correcto de concurrencia,
- perfilamiento,
- y cobertura de pruebas sin danos colaterales sobre historias previas.
