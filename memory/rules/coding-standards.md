# Reglas de Codigo

## Kotlin

- Preferir `val`.
- Evitar `!!`.
- No usar wildcard imports.
- Mantener funciones pequenas y cohesivas.
- Nombrar por dominio.

## Android

- No retener binding fuera del ciclo de vida de la vista.
- No hacer trabajo pesado en UI thread.
- Strings visibles en recursos.
- No hardcodear secretos ni configuraciones sensibles.

## Diseno

- Aplicar SOLID cuando reduzca acoplamiento y mejore mantenimiento.
- Aplicar GRASP para ubicar responsabilidades donde corresponden.
- Usar patrones GoF solo cuando resuelven un problema real, no por ornamento.

## Limpieza

- Remover codigo muerto si se toca la zona y el impacto es seguro.
- No introducir abstracciones prematuras.
