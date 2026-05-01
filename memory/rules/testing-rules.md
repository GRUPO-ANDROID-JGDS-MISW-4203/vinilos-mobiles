# Reglas de Pruebas

## Regla 1

Todo cambio funcional debe evaluar impacto en pruebas.

## Regla 2

Cambios de UI navegable deben considerar Espresso.

## Regla 3

Cambios en integracion REST deben considerar `MockWebServer` o equivalente presente.

## Regla 4

Lectura importante debe cubrir happy path y error path.

## Regla 5

Escritura importante debe cubrir validacion, exito y error.

## Regla 6

Si no se pueden ejecutar pruebas, la razon debe quedar explicitada.
