# Reglas de Arquitectura

## Regla 1

Mantener orientacion MVVM con separacion real entre UI, ViewModel, Repository y red.

## Regla 2

La UI no llama Retrofit ni resuelve parsing de red.

## Regla 3

El `ViewModel` no referencia `View`, `Binding`, adapters ni widgets.

## Regla 4

El `Repository` es el punto de entrada a datos remotos o locales.

## Regla 5

No mezclar decisiones de navegacion con logica de negocio mas alla del disparo del evento.

## Regla 6

Si se agrega persistencia local, entrar por `repository/` y documentar el criterio.

## Regla 7

No crear nuevas capas o paquetes si la necesidad no es clara y sostenida.
