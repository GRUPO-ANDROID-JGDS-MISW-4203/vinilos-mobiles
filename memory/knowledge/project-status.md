# Estado del Proyecto

## Estado documental

- Existe `AGENTS.md` como guia principal.
- `memory/` es la base documental operativa actual.
- La wiki historica del proyecto registra inception y sprints planificados.

## Estado tecnico observado

- Proyecto Android con un solo modulo `app`.
- Estructura principal activa:
  - `ui/`
  - `viewmodel/`
  - `repository/`
  - `network/`
  - `model/`
- Features visibles por dominio:
  - albumes,
  - artistas,
  - coleccionistas.
- Hay pruebas instrumentadas ya presentes para algunas features.

## Gap principal actual

La app y la wiki muestran una intencion clara de MVVM y cobertura del MVP, pero la base de reglas y memoria para asistentes AI no estaba suficientemente profunda.
Esta carpeta corrige ese gap.

## Expectativa de uso

Todo agente que toque este repo debe:

- partir del estado real,
- respetar arquitectura,
- mantener trazabilidad a HU y sprint,
- y no separar codigo de pruebas y documentacion.
