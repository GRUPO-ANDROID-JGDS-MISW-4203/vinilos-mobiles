# Reglas de Asincronia y Desempeno

## Regla 1

No ejecutar red, disco ni procesamiento pesado en el hilo principal.

## Regla 2

Toda operacion larga debe tener ownership claro de ciclo de vida.

## Regla 3

`GlobalScope` no es la opcion por defecto.

## Regla 4

El cache debe tener objetivo claro: latencia, ahorro de red, soporte offline parcial o reduccion de CPU.

## Regla 5

No usar cache sin definir invalidez, eviccion o al menos alcance.

## Regla 6

Las micro-optimizaciones deben ser medibles o justificables, no cosmeticas.

## Regla 7

Si una imagen o recurso remoto ya tiene una libreria de cache adecuada en el stack, no reinventar el mecanismo manualmente.
