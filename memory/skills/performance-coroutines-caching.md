# Skill: Performance, Corrutinas y Caching

## Objetivo

Evitar degradaciones de desempeno y decisiones ingenuas de asincronia o cache.

## Corrutinas

- Red, IO y operaciones pesadas fuera del hilo principal.
- Evitar secuencias largas bloqueantes en UI.
- Controlar cancelacion cuando el ciclo de vida invalida el resultado.

## Desempeno de UI

- No hacer trabajo pesado dentro de `onBindViewHolder`.
- Evitar recrear objetos innecesarios en scroll.
- Cargar imagenes con libreria de cache existente.
- Evitar layouts costosos sin necesidad.

## Cache

- Solo usar cache cuando reduce costo o mejora experiencia.
- Definir que se cachea, por cuanto tiempo y cuando se invalida.
- No usar cache para ocultar bugs de navegacion o estado.
- Si no existe persistencia local, sea explicito: cache en memoria no es offline real.

## Memoria

- No retener bindings, views o contextos innecesariamente.
- No cargar colecciones gigantes cuando la pantalla no lo necesita.
- Reutilizar modelos y transformaciones razonablemente.

## Cuando endurecer desempeno

- listas con imagenes,
- pantallas con scroll frecuente,
- llamadas repetitivas al backend,
- formularios con varias cargas dependientes.

## Checklist

- No hay trabajo pesado en main thread.
- La experiencia visual sigue fluida.
- El uso de cache, si existe, tiene criterio claro.
