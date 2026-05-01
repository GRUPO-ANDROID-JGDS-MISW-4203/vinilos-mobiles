# Memory Base

`memory/` es la base documental operativa del repositorio para agentes AI y para el equipo.
Su objetivo es reemplazar de forma ordenada la ausencia actual de `docs/`, `./.codex/` y `./.cursor/`, sin inventar una arquitectura documental innecesaria.

## Estructura

```text
memory/
  README.md
  skills/
  rules/
  policies/
  knowledge/
  archive/
```

## Orden recomendado de consulta

1. `AGENTS.md`
2. `memory/README.md`
3. `memory/rules/`
4. `memory/policies/`
5. `memory/skills/`
6. `memory/knowledge/`
7. `memory/archive/` solo para contexto historico

## Principio de uso

- `rules/` responde que se puede tocar y que restricciones existen.
- `policies/` responde que debe cumplirse para aceptar un cambio.
- `skills/` responde como implementar o validar por area tecnica.
- `knowledge/` responde el contexto del curso, del proyecto y del repo.
- `archive/` conserva material historico que no gobierna automaticamente el presente.

## Reglas de mantenimiento

- Si una regla es estable y accionable, va a `rules/`.
- Si define gates o definition of done, va a `policies/`.
- Si explica un flujo reusable de trabajo, va a `skills/`.
- Si describe teoria, contexto, roadmap o estado, va a `knowledge/`.
- Si ya no gobierna el presente pero conviene guardarlo, va a `archive/`.
