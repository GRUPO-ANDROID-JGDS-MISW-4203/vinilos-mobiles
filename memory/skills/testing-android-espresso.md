# Skill: Testing Android con Espresso

## Objetivo

Definir como validar cambios funcionales y de UI en este repositorio.

## Estrategia base

- Unitarias para logica de validacion, mappers y comportamiento de ViewModel/Repository.
- Instrumentadas para flujos UI, navegacion, render y estados.
- `MockWebServer` para aislar dependencias del backend.

## Cuando escribir o ajustar tests

- Nueva pantalla.
- Cambio de navegacion.
- Cambio de estado de carga o error.
- Cambio de contrato HTTP que impacta render.
- Cambio de validaciones de formularios.

## Enfoque para Espresso

- Verificar visibilidad de elementos clave.
- Verificar navegacion al tocar un item.
- Verificar estados de carga con respuestas demoradas.
- Verificar estados de error con respuestas fallidas.
- Verificar contenido visible relevante de la historia.

## MockWebServer

- Use respuestas JSON desde `src/androidTest/assets`.
- Mantenga fixtures pequenas, estables y entendibles.
- Modele tanto happy path como error path.

## Casos minimos por HU

### Lectura

- lista o detalle visible,
- carga inicial,
- manejo de error,
- navegacion relevante.

### Escritura

- validacion de campos,
- submit exitoso,
- submit fallido,
- persistencia del estado o mensaje de error.

## Criterios de calidad de prueba

- Test con nombre explicito.
- Un escenario principal por test.
- Fixtures controladas.
- Sin dependencia innecesaria de backend real.

## Soporte adicional

- `ADB` para captura de evidencia.
- `Firebase Test Lab` cuando se deba validar fragmentacion o ejecucion cloud.

## Checklist

- El cambio tiene al menos la cobertura razonable esperada.
- Los tests existentes siguen alineados con la UI.
- Hay evidencia de ejecucion o una explicacion concreta si no se pudo correr.
