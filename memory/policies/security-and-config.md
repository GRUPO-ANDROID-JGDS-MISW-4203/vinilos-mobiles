# Politica de Seguridad y Configuracion

## Secretos

- No hardcodear tokens, passwords o secretos.
- No subir credenciales reales al repo.

## Red y config

- Base URLs y configuraciones sensibles deben poder centralizarse.
- No dispersar configuracion de red por fragments o adapters.

## Dependencias

- No agregar librerias nuevas sin justificar necesidad.
- Preferir el stack ya presente si resuelve el problema.

## Integridad del repo

- No editar artefactos generados salvo que sea parte intencional del flujo.
- No introducir configuraciones que rompan el run flow documentado.
