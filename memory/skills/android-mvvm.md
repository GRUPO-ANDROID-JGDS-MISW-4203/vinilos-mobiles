# Skill: Android MVVM

## Objetivo

Guiar implementaciones y refactors con enfoque MVVM realista para este repositorio.

## Cuando aplicar

- Nuevas pantallas.
- Ajustes de flujo en fragments existentes.
- Correcciones de mezcla de responsabilidades entre UI, ViewModel y Repository.
- Incorporacion de nuevas historias HU01-HU08 o endurecimiento de las ya presentes.

## Estructura objetivo

- `ui/`: render, listeners, navegacion, binding y adapters.
- `viewmodel/`: estados de pantalla, coordinacion de carga/guardado y eventos de UI.
- `repository/`: fuente unica de acceso a datos remotos o locales.
- `network/`: Retrofit, DTOs, configuracion HTTP y adapters.
- `model/`: modelos de dominio o transporte.

## Responsabilidades por capa

### View / Fragment

- Inicializa binding y observa `LiveData`.
- Dispara acciones de UI al ViewModel.
- Muestra loading, contenido, empty state y error.
- Navega cuando el estado o el evento lo indiquen.
- No parsea JSON, no crea clientes HTTP, no aplica reglas de negocio complejas.

### ViewModel

- Expone estado observable.
- Orquesta carga inicial, retry y acciones del usuario.
- Traduce resultados del repository a estados de pantalla.
- No referencia widgets, bindings ni vistas concretas.

### Repository

- Decide de donde salen los datos.
- Encapsula acceso remoto y futuro acceso local.
- Centraliza transformaciones necesarias entre red y consumo de app.
- No debe filtrar detalles HTTP a la UI salvo errores traducidos.

## Estados de pantalla recomendados

Para cada pantalla importante pensar explicitamente en:

- `Loading`
- `Success`
- `Empty`
- `Error`

Si no se modela formalmente con `sealed class`, al menos debe existir una separacion clara de estas salidas.

## Flujo recomendado

1. La UI se crea.
2. El `Fragment` solicita al `ViewModel` cargar o refrescar.
3. El `ViewModel` publica estado de carga.
4. El `Repository` consulta red o cache.
5. El `ViewModel` transforma la respuesta en estado de exito, vacio o error.
6. La UI renderiza el estado.

## Smells que deben corregirse

- `Fragment` llamando directo a Retrofit.
- `Fragment` tomando decisiones de parseo o mapping.
- `ViewModel` con referencias a `View`, `Binding` o `Context` de UI.
- `Repository` devolviendo respuestas crudas de Retrofit cuando el resto de la app necesita modelos de dominio.
- Reglas de validacion repartidas entre XML, Fragment y Repository sin una fuente clara.

## Checklist antes de cerrar un cambio MVVM

- La UI solo renderiza y delega.
- El `ViewModel` coordina, no dibuja.
- El `Repository` concentra acceso a datos.
- La navegacion sigue funcionando.
- Existe manejo de loading y error.
- Hay pruebas o evidencia razonable del flujo principal.
