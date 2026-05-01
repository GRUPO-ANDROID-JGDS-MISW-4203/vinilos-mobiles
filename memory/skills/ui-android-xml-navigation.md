# Skill: UI Android XML y Navigation

## Objetivo

Guiar cambios en layouts XML, fragments, adapters y Navigation Component.

## Reglas de UI

- La UI debe ser clara, sobria y consistente con Android nativo.
- Todo estado remoto debe contemplar loading y error.
- El usuario debe poder entender que esta pasando si una carga falla.
- Las pantallas deben respetar jerarquia visual y scroll util.

## Layouts XML

- Use `strings.xml` para texto visible.
- Evite nesting excesivo si complica mantenimiento.
- Mantenga ids estables si son consumidos por Espresso.
- Use componentes Material cuando ya existan en el proyecto y resuelvan el caso.

## Fragments

- El `Fragment` prepara observers, listeners y navegacion.
- No coloque reglas de negocio complejas en callbacks de botones.
- Mantenga separadas funciones de setup, observers y render.

## Navigation Component

- Toda navegacion debe pasar por el nav graph.
- Revise argumentos y acciones al cambiar rutas.
- Si cambia un id de destino o de action, revise pruebas y lugares de invocacion.
- Evite navegacion duplicada o disparos multiples por taps repetidos.

## Adapters

- Deben encargarse de bind y eventos simples.
- No deben hacer llamadas de red ni cargar logica de negocio.
- Evite transformaciones pesadas por item.
- Si muestra imagen remota, use la libreria del proyecto.

## Accesibilidad y testabilidad

- Mantenga textos, ids y descripciones utiles.
- Los elementos criticos deben ser facilmente localizables por pruebas.
- No esconda errores con toasts fugaces si la recuperacion requiere atencion.

## Checklist

- La pantalla renderiza loading, content y error.
- La navegacion sigue intacta.
- Los ids relevantes no se rompieron sin actualizar tests.
- Los strings visibles no quedaron hardcodeados.
