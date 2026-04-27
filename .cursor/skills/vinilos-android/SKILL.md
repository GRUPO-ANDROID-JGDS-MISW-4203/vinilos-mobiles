---
name: vinilos-android
description: Desarrollo de la aplicación Vinilos para Android con Kotlin. Usa este skill cuando trabajes en funcionalidades de la app, implementes historias de usuario (HU01-HU08), o necesites guía sobre el proyecto TSDC. Aplica a Kotlin, Android, MVVM, Retrofit, Room, LiveData.
---

# Vinilos Android - Skill Orquestadora

Este skill es el punto de entrada para trabajar en Vinilos sin duplicar contenido de skills especializadas.

## Cuando usar este skill

- Para decidir por donde empezar una HU.
- Para validar que una entrega tenga arquitectura, pruebas y documentacion completas.
- Para enrutar el trabajo a la skill correcta.

## Skills especializadas (fuente unica)

1. Arquitectura MVVM: `../mvvm-architecture/SKILL.md`
2. API REST y Retrofit: `../api-rest-android/SKILL.md`
3. Pruebas E2E Espresso: `../espresso-testing/SKILL.md`
4. Estrategia integral de pruebas: `../testing-strategy-android/SKILL.md`
5. Historias de usuario HU01-HU08: `../user-stories/SKILL.md`
6. Flujo GitFlow: `../gitflow-workflow/SKILL.md`

## Flujo recomendado por historia de usuario

1. Revisar alcance funcional en `user-stories`.
2. Implementar estructura con `mvvm-architecture`.
3. Conectar backend con `api-rest-android`.
4. Crear pruebas con `testing-strategy-android` y `espresso-testing`.
5. Preparar integración con `gitflow-workflow`.
6. Actualizar README/Wiki según `.cursor/rules/documentation-quality.mdc`.

## Checklist de salida (idempotente)

- [ ] No se duplicaron snippets entre skills.
- [ ] Se usaron skills especializadas por tema.
- [ ] README y Wiki se actualizaron si el cambio lo requiere.
- [ ] La HU queda trazada a pruebas y evidencia.
