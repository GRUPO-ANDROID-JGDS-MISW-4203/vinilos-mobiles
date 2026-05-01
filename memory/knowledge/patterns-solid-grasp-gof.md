# SOLID, GRASP y GoF aplicados al proyecto

## SOLID

### Single Responsibility

Cada clase debe tener una razon clara para cambiar.
Ejemplo: un `Fragment` no debe cargar datos, mapear DTOs y navegar todo junto.

### Open/Closed

Extender comportamiento por composicion o nuevas piezas cuando sea razonable.
No introducir ifs crecientes en UI por cada nueva historia si se puede aislar la logica.

### Liskov

Las abstracciones deben mantener contratos coherentes.

### Interface Segregation

No crear interfaces gigantes para repositories o adapters si cada cliente usa una minima parte.

### Dependency Inversion

La logica de alto nivel no debe depender de detalle de Retrofit o widget concreto.

## GRASP

### Controller

El `ViewModel` es el controlador natural del flujo de pantalla.

### Information Expert

El repository o mapper que conoce la estructura de datos debe transformarlos, no la vista.

### Low Coupling

La UI no debe conocer detalles HTTP ni almacenamiento.

### High Cohesion

Cada clase o archivo debe hacer pocas cosas y hacerlas bien.

## GoF utiles aqui

### Observer

`LiveData` y observacion lifecycle-aware.

### Adapter

Adaptacion entre API REST y modelos de la aplicacion.

### Strategy

Valido para validaciones o politicas de cache cuando la logica crece.

### Factory

Valido para construir modelos de request o estados de UI complejos.

## Regla practica

No fuerce patrones por estetica.
Use el patron solo si resuelve acoplamiento, claridad o testabilidad.
