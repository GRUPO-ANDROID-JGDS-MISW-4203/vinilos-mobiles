# Configuración de Cursor para Vinilos

Esta carpeta contiene la configuración de reglas y skills para el agente AI de Cursor, optimizada para el desarrollo del proyecto Vinilos.

## Estructura

```
.cursor/
├── README.md                    # Este archivo
├── knowledge-map.md             # Fuente unica por tema (idempotencia)
├── rules/                       # Reglas que aplican automáticamente
│   ├── kotlin-android-standards.mdc   # Estándares de código Kotlin
│   ├── mvvm-architecture.mdc          # Reglas de arquitectura MVVM
│   ├── espresso-testing.mdc           # Reglas para pruebas E2E
│   ├── gitflow-workflow.mdc           # Reglas de GitFlow
│   ├── xml-layouts.mdc                # Estándares para XML layouts
│   ├── project-context.mdc            # Contexto general del proyecto
│   └── documentation-quality.mdc      # Reglas de documentacion y calidad
│
└── skills/                      # Skills para tareas específicas
    ├── vinilos-android/         # Skill principal del proyecto
    │   ├── SKILL.md             # Desarrollo de funcionalidades
    │   ├── TEORIA.md            # Conceptos teóricos del curso
    │   └── MINI-AGENTS.md       # Templates para tareas comunes
    │
    ├── mvvm-architecture/       # Implementar arquitectura MVVM
    │   └── SKILL.md
    │
    ├── api-rest-android/        # Consumir APIs REST con Retrofit
    │   └── SKILL.md
    │
    ├── espresso-testing/        # Crear pruebas E2E
    │   └── SKILL.md
    │
    ├── user-stories/            # Implementar HU01-HU08
    │   └── SKILL.md
    │
    ├── gitflow-workflow/        # Flujo de trabajo Git
    │   └── SKILL.md
    │
    └── testing-strategy-android/ # Estrategia integral de pruebas
        └── SKILL.md
```

## Reglas (Rules)

Las reglas se aplican automáticamente según su configuración:

| Regla | Aplica a | Auto |
|-------|----------|------|
| `kotlin-android-standards.mdc` | `**/*.kt` | Por glob |
| `mvvm-architecture.mdc` | Siempre | Siempre |
| `espresso-testing.mdc` | `**/androidTest/**/*.kt` | Por glob |
| `gitflow-workflow.mdc` | Siempre | Siempre |
| `xml-layouts.mdc` | `**/res/layout/**/*.xml` | Por glob |
| `project-context.mdc` | Siempre | Siempre |
| `documentation-quality.mdc` | Siempre | Siempre |

## Skills

Los skills se activan cuando son relevantes para la tarea:

| Skill | Trigger |
|-------|---------|
| `vinilos-android` | Desarrollo de funcionalidades, HUs |
| `mvvm-architecture` | ViewModels, LiveData, Repositories |
| `api-rest-android` | Retrofit, llamadas HTTP, JSON |
| `espresso-testing` | Pruebas E2E, UI testing |
| `user-stories` | Implementar HU01-HU08 |
| `gitflow-workflow` | Git, branches, PRs, commits |
| `testing-strategy-android` | Estrategia pruebas, evidencias, reportes |

## Archivo Principal

Además de esta carpeta, existe el archivo `AGENTS.md` en la raíz del proyecto que contiene la guía principal para agentes AI.

## Uso

1. **Desarrollo normal**: Las reglas se aplican automáticamente
2. **Tareas específicas**: Los skills se activan por contexto
3. **Consulta manual**: Referir a `AGENTS.md` para visión general
4. **Idempotencia**: Revisar `knowledge-map.md` antes de crear nuevas reglas/skills

## Personalización

- Editar reglas en `.cursor/rules/*.mdc`
- Editar skills en `.cursor/skills/*/SKILL.md`
- Agregar nuevas reglas/skills según necesidad del proyecto

## Notas

- Las reglas con `alwaysApply: true` aplican en toda conversación
- Las reglas con `globs:` aplican solo a archivos que coincidan
- Los skills se leen bajo demanda según el contexto
