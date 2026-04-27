---
name: testing-strategy-android
description: Definir estrategia de pruebas Android (unitarias, integracion y E2E), evidencias y reporte de resultados por HU/sprint.
---

# Testing Strategy Android

Usa este skill cuando necesites definir, ejecutar o documentar estrategia de pruebas completa para Vinilos.

## Alcance

- Pruebas unitarias (`test`)
- Pruebas de instrumentacion y E2E (`androidTest`, Espresso)
- Evidencia de resultados para README/Wiki

## Piramide recomendada

1. Unitarias: ViewModel, validaciones, mappers, repositorios con fakes.
2. Integracion: DAO Room y repositorio con fuente de datos simulada.
3. E2E: flujos HU en UI con Espresso.

## Minimo por HU

- 1 prueba unitaria de logica principal.
- 1 prueba de error o validacion.
- 1 escenario E2E feliz (si la HU tiene UI).

## Estructura de paquetes sugerida

```text
app/src/test/java/com/tsdc/vinilos/...
app/src/androidTest/java/com/tsdc/vinilos/...
app/src/androidTest/assets/...
```

## Comandos base

```bash
./gradlew test
./gradlew connectedAndroidTest
./gradlew lint
```

## Plantilla de reporte de resultados

```markdown
## Resultados de pruebas - Sprint X

Fecha: YYYY-MM-DD

- Unitarias (`./gradlew test`): PASS/FAIL
- E2E (`./gradlew connectedAndroidTest`): PASS/FAIL
- Lint (`./gradlew lint`): PASS/FAIL

Cobertura HU:
- HU01: PASS/FAIL - evidencia
- HU02: PASS/FAIL - evidencia
```

## Buenas practicas

- Evitar duplicar escenarios entre test unitarios y E2E.
- Mantener nombres de pruebas por comportamiento esperado.
- Reportar defectos con issue y pasos de reproduccion.
