# Skill: Corrutinas y Concurrencia

## Objetivo

Aplicar concurrencia en Kotlin sin bloquear el hilo principal y sin romper el ciclo de vida Android.

## Cuando usar

- Llamadas de red.
- Acceso a disco o base de datos.
- Parsing pesado.
- Cargas concurrentes de varios recursos.

## Regla central

No ejecutar tareas largas en `Main`.

## Scopes preferidos

- `viewModelScope` para trabajo iniciado desde `ViewModel`.
- `lifecycleScope` para trabajo ligado a `Activity` o `Fragment`.
- Evitar `GlobalScope` salvo procesos realmente globales y justificados.

## Dispatchers

- `Dispatchers.Main` para actualizar UI.
- `Dispatchers.IO` para red, archivos y almacenamiento.
- `Dispatchers.Default` para trabajo intensivo de CPU.

## Builders

- `launch` para fire-and-forget con manejo de estado externo.
- `async` cuando se necesita un valor y luego `await`.
- Evitar `runBlocking` en codigo productivo Android.

## Patron recomendado

1. La UI dispara una accion.
2. El `ViewModel` lanza corrutina.
3. La operacion pesada va a `IO` o `Default`.
4. El resultado vuelve a estado observable.
5. La UI renderiza.

## Riesgos a evitar

- `try/catch` fuera del bloque real de suspension.
- Uso de `GlobalScope`.
- Trabajo de red dentro de `Dispatchers.Main`.
- Corrutinas sin cancelacion ligada al ciclo de vida.
- Mezclar logica de UI con cambio de dispatcher en el fragment.

## Checklist

- Ninguna tarea larga bloquea la UI.
- El scope corresponde al componente dueño del flujo.
- El dispatcher corresponde al tipo de trabajo.
