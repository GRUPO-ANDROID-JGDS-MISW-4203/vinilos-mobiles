# Reglas de Repository

## Regla 1

Todo acceso a datos debe pasar por un repository cuando la feature ya sigue esa linea.

## Regla 2

El repository decide uso de red, cache y futuro almacenamiento local.

## Regla 3

El repository no debe exponer detalles HTTP innecesarios a la UI.

## Regla 4

Si una respuesta remota necesita adaptacion, hacerla en el repository o mapper asociado, no en el fragment.

## Regla 5

Si se implementa cache:

- definir invalidez,
- evitar inconsistencia silenciosa,
- y documentar si es cache en memoria o persistencia local real.
