# Quality Gates

## Gate 1: Build

- El proyecto debe compilar.
- No deben quedar errores de compilacion en el modulo tocado.

## Gate 2: Arquitectura

- El cambio respeta separacion MVVM.
- No se empuja logica de negocio a la UI.
- No se introducen atajos que rompan coherencia de capas.

## Gate 3: UX funcional

- La pantalla o flujo conserva loading, contenido y error cuando aplica.
- La navegacion no queda rota.

## Gate 4: Pruebas

- Se ejecutan pruebas relevantes o se explica por que no fue viable.
- Si el cambio afecta UI o REST, debe existir evidencia razonable de validacion.

## Gate 5: Documentacion

- README o memory deben reflejar cambios estructurales relevantes.
- No debe quedar documentacion afirmando cosas falsas del repo.

## Gate 6: Mantenibilidad

- No introducir duplicacion evitable.
- No dejar codigo muerto obvio.
- No dejar nombres confusos o dependencias innecesarias.
