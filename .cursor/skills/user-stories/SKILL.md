---
name: user-stories
description: Implementar historias de usuario HU01-HU08 del proyecto Vinilos. Usa este skill cuando trabajes en una historia de usuario específica, necesites entender los requisitos, o crear los componentes necesarios. Aplica a HU01, HU02, HU03, HU04, HU05, HU06, HU07, HU08, backlog, sprint.
---

# Historias de Usuario - Vinilos MVP

## Resumen del MVP

| Sprint | Historias | Puntos |
|--------|-----------|--------|
| Sprint 1 | HU01, HU02 | 13 |
| Sprint 2 | HU03, HU04, HU05 | 13 |
| Sprint 3 | HU06, HU07, HU08 | 13 |

---

## HU01 - Consultar Catálogo de Álbumes

### Descripción
> Como **usuario visitante** quiero **navegar el catálogo de los álbumes** para **escoger los que más me interesan**

### Criterios de Aceptación
1. Se muestra una lista de álbumes con portada, nombre y año
2. La lista es scrollable
3. Las imágenes cargan correctamente
4. Se muestra un indicador de carga mientras se obtienen datos
5. Se muestra mensaje de error si falla la conexión

### Componentes
- `AlbumsFragment`
- `AlbumsViewModel`
- `AlbumsRepository`
- `AlbumServiceAdapter`
- `AlbumAdapter` (RecyclerView)
- `item_album.xml`
- `fragment_albums.xml`

### API
```
GET /albums
Response: List<Album>
```

---

## HU02 - Consultar Detalle de Álbum

### Descripción
> Como **usuario visitante** quiero **ver el detalle de un álbum** para **ampliar la información sobre él**

### Criterios de Aceptación
1. Al hacer tap en un álbum se navega al detalle
2. Se muestra: portada, nombre, año, género, sello, descripción
3. Se muestra lista de tracks (si tiene)
4. Se muestra lista de artistas asociados
5. Botón/gesto para volver a la lista

### Componentes
- `AlbumDetailFragment`
- `AlbumDetailViewModel`
- `fragment_album_detail.xml`

### API
```
GET /albums/{id}
Response: Album (con tracks y performers)
```

---

## HU03 - Consultar Listado de Artistas

### Descripción
> Como **usuario visitante** quiero **ver el listado de artistas** para **escoger los que más me interesan**

### Criterios de Aceptación
1. Se muestra lista de artistas con foto y nombre
2. La lista es scrollable
3. Las imágenes cargan correctamente
4. Se muestra indicador de carga

### Componentes
- `ArtistsFragment`
- `ArtistsViewModel`
- `ArtistsRepository`
- `MusicianServiceAdapter`
- `ArtistAdapter`
- `item_artist.xml`
- `fragment_artists.xml`

### API
```
GET /musicians
Response: List<Musician>
```

---

## HU04 - Consultar Detalle de Artista

### Descripción
> Como **usuario visitante** quiero **ver el detalle de un artista** para **ampliar la información sobre él**

### Criterios de Aceptación
1. Al hacer tap en un artista se navega al detalle
2. Se muestra: foto, nombre, fecha nacimiento, descripción
3. Se muestra lista de álbumes del artista
4. Botón/gesto para volver a la lista

### Componentes
- `ArtistDetailFragment`
- `ArtistDetailViewModel`
- `fragment_artist_detail.xml`

### API
```
GET /musicians/{id}
Response: Musician (con albums)
```

---

## HU05 - Consultar Listado de Coleccionistas

### Descripción
> Como **usuario visitante** quiero **consultar el listado de coleccionistas** para **seleccionar el que más me interese**

### Criterios de Aceptación
1. Se muestra lista de coleccionistas con nombre y email
2. La lista es scrollable
3. Se muestra indicador de carga

### Componentes
- `CollectorsFragment`
- `CollectorsViewModel`
- `CollectorsRepository`
- `CollectorServiceAdapter`
- `CollectorAdapter`
- `item_collector.xml`
- `fragment_collectors.xml`

### API
```
GET /collectors
Response: List<Collector>
```

---

## HU06 - Consultar Detalle de Coleccionista

### Descripción
> Como **usuario visitante** quiero **ver el detalle de un coleccionista** para **conocer sus gustos musicales**

### Criterios de Aceptación
1. Al hacer tap en coleccionista se navega al detalle
2. Se muestra: nombre, teléfono, email
3. Se muestra lista de álbumes del coleccionista
4. Se muestra lista de artistas favoritos
5. Botón/gesto para volver

### Componentes
- `CollectorDetailFragment`
- `CollectorDetailViewModel`
- `fragment_collector_detail.xml`

### API
```
GET /collectors/{id}
Response: Collector (con albums y favoritePerformers)
```

---

## HU07 - Crear un Álbum

### Descripción
> Como **coleccionista** quiero **agregar un álbum al listado**

### Criterios de Aceptación
1. Solo visible para rol Coleccionista
2. Formulario con campos: nombre, portada, fecha, descripción, género, sello
3. Validación de campos requeridos
4. Mensaje de éxito/error al crear
5. Navega a lista después de crear exitosamente

### Componentes
- `CreateAlbumFragment`
- `CreateAlbumViewModel`
- `fragment_create_album.xml`

### API
```
POST /albums
Body: { name, cover, releaseDate, description, genre, recordLabel }
Response: Album
```

### Validaciones
- Nombre: requerido, mínimo 3 caracteres
- Portada: requerido, URL válida
- Fecha: requerido, formato válido
- Género: requerido, selección de lista
- Sello: requerido, selección de lista

---

## HU08 - Asociar Tracks con un Álbum

### Descripción
> Como **coleccionista** quiero **agregar tracks a un álbum** para **actualizar el catálogo**

### Criterios de Aceptación
1. Solo visible para rol Coleccionista
2. Desde detalle de álbum, opción para agregar track
3. Formulario con: nombre del track, duración
4. Validación de campos
5. Track aparece en lista del álbum después de agregar

### Componentes
- `AddTrackFragment`
- `AddTrackViewModel`
- `fragment_add_track.xml`

### API
```
POST /albums/{id}/tracks
Body: { name, duration }
Response: Track
```

### Validaciones
- Nombre: requerido
- Duración: requerido, formato MM:SS

---

## Checklist de Implementación por HU

```markdown
## HU0X - [Nombre]

### Backend/Data
- [ ] DTO creado
- [ ] Modelo de dominio creado
- [ ] Endpoint en VinilosApi
- [ ] ServiceAdapter implementado
- [ ] Repository implementado

### UI
- [ ] Fragment creado
- [ ] ViewModel creado
- [ ] Layout XML diseñado
- [ ] Adapter (si es lista)
- [ ] Navegación configurada

### Calidad
- [ ] Pruebas E2E en Espresso
- [ ] Manejo de errores
- [ ] Estado de carga visible
- [ ] Sin warnings de Lint

### Integración
- [ ] PR creado
- [ ] Code review aprobado
- [ ] Merge a develop
```

---

## Roles de Usuario

### Visitante
- Ver catálogo de álbumes (HU01)
- Ver detalle de álbum (HU02)
- Ver listado de artistas (HU03)
- Ver detalle de artista (HU04)
- Ver listado de coleccionistas (HU05)
- Ver detalle de coleccionista (HU06)

### Coleccionista
- Todo lo del Visitante +
- Crear álbum (HU07)
- Agregar tracks (HU08)

### Implementación de Roles

```kotlin
// UserRole.kt
enum class UserRole(val displayName: String) {
    VISITANTE("Visitante"),
    COLECCIONISTA("Coleccionista")
}

// En Fragment
private fun checkRole() {
    val role = (activity as MainActivity).getCurrentRole()
    binding.fabCreate.isVisible = role == UserRole.COLECCIONISTA
}
```
