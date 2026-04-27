# Matriz de Idempotencia: Rules y Skills

Este documento define la fuente unica por tema para evitar duplicidad entre reglas, skills y guias generales.

## Fuente unica por tema

| Tema | Regla (norma obligatoria) | Skill (procedimiento) | Referencia general |
|---|---|---|---|
| Contexto del proyecto | `.cursor/rules/project-context.mdc` | `.cursor/skills/vinilos-android/SKILL.md` (solo indice) | `AGENTS.md` |
| Arquitectura MVVM | `.cursor/rules/mvvm-architecture.mdc` | `.cursor/skills/mvvm-architecture/SKILL.md` | `AGENTS.md` |
| API REST y Service Adapter | `AGENTS.md` (criterio alto nivel) | `.cursor/skills/api-rest-android/SKILL.md` | `.cursor/rules/project-context.mdc` |
| Pruebas E2E Espresso | `.cursor/rules/espresso-testing.mdc` | `.cursor/skills/espresso-testing/SKILL.md` | `AGENTS.md` |
| Estrategia de pruebas integral | `.cursor/rules/documentation-quality.mdc` | `.cursor/skills/testing-strategy-android/SKILL.md` | `README.md` |
| GitFlow | `.cursor/rules/gitflow-workflow.mdc` | `.cursor/skills/gitflow-workflow/SKILL.md` | `README.md` |
| Documentacion y evidencia | `.cursor/rules/documentation-quality.mdc` | `.cursor/skills/vinilos-android/SKILL.md` (checklist de salida) | `README.md`, `wiki/*.md` |

## Regla de mantenimiento

1. Si un tema ya tiene skill especializada, no duplicar ejemplos largos en skills generales.
2. Las reglas solo definen normas y criterios de aceptacion; los pasos detallados van en skills.
3. La documentacion publica (README/Wiki) es la evidencia de entrega; `.cursor/` es guia operativa del agente.
