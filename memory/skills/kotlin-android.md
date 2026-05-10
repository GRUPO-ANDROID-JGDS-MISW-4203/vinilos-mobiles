# Skill: Kotlin Android

## Objetivo

Definir lineamientos de implementacion Kotlin para Android nativo en este proyecto.

## Principios de estilo

- Preferir `val` sobre `var`.
- Preferir expresiones claras sobre clever code.
- Nombrar por dominio, no por detalle tecnico.
- Mantener funciones pequenas y con una sola intencion.
- Evitar `!!` salvo razon muy puntual y controlada.
- Evitar clases utilitarias gigantes.
- No usar wildcard imports.

## Null safety

- Modele nullable solo donde el dominio realmente lo requiere.
- Use `?.`, `?:`, `let`, `takeIf` y validaciones tempranas antes de caer en `!!`.
- Si un dato es obligatorio para renderizar o guardar, validelo temprano y falle de forma controlada.

## Data classes y modelos

- Use `data class` para modelos inmutables de transporte o dominio cuando aplique.
- Separe request models de response models si la API lo exige.
- No agregue comportamiento Android a modelos de dominio salvo necesidad real.

## Funciones y extensiones

- Use extensiones cuando reduzcan duplicacion y mejoren legibilidad.
- No convierta extensiones en un basurero de helpers sin cohesion.
- Prefiera mappers explicitos cuando transforman contratos API a modelos de app.

## Corrutinas

- Toda operacion de red o IO debe ejecutarse fuera del hilo principal.
- Controle propagacion de errores.
- Evite lanzar corrutinas sin ownership claro del ciclo de vida.

## Android specifics

- Limpiar bindings en `onDestroyView` si el fragment usa view binding.
- No guardar referencias largas a views.
- No ejecutar trabajo pesado en adapters.
- No hardcodear strings visibles ni dimensiones semanticas importantes.

## Buenas practicas de programacion

- Reducir duplicacion.
- Diseñar para lectura y mantenimiento.
- Fallar de forma explicita cuando la entrada es invalida.
- Dejar comentarios solo cuando el codigo no sea suficiente por si mismo.
- Preferir composicion sobre herencia innecesaria.

## Checklist

- El codigo es idiomatico Kotlin sin volverse rebuscado.
- La nullability esta controlada.
- El ciclo de vida Android esta respetado.
- No hay deuda obvia por atajos inseguros.
