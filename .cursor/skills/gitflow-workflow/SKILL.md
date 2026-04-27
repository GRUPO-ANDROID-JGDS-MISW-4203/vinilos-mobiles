---
name: gitflow-workflow
description: Flujo de trabajo GitFlow para el proyecto Vinilos. Usa este skill cuando necesites crear ramas, hacer commits, crear PRs, o manejar releases. Aplica a Git, GitFlow, branching, commits, pull requests, code review.
---

# GitFlow Workflow Skill

## Estructura de Ramas

```
main ─────────────────────────────────────────────► (producción)
  │
  └── develop ────────────────────────────────────► (integración)
        │
        ├── feature/HU01-catalogo-albumes ────────► (desarrollo)
        ├── feature/HU02-detalle-album
        ├── bugfix/fix-crash-detalle
        └── release/sprint-1
```

## Comandos por Flujo

### 1. Iniciar Nueva Feature

```bash
# Asegurar estar actualizado
git checkout develop
git pull origin develop

# Crear rama de feature
git checkout -b feature/HU01-catalogo-albumes

# Verificar rama actual
git branch
```

### 2. Desarrollo en Feature

```bash
# Commits atómicos y frecuentes
git add app/src/main/java/com/tsdc/vinilos/ui/albums/
git commit -m "feat(albums): crear AlbumsFragment con RecyclerView"

git add app/src/main/java/com/tsdc/vinilos/data/
git commit -m "feat(albums): implementar AlbumsRepository y ServiceAdapter"

git add app/src/androidTest/
git commit -m "test(albums): agregar pruebas E2E con Espresso"

# Push al remoto
git push origin feature/HU01-catalogo-albumes
```

### 3. Mantener Feature Actualizada

```bash
# Traer cambios de develop
git checkout develop
git pull origin develop
git checkout feature/HU01-catalogo-albumes
git rebase develop

# Resolver conflictos si los hay
git add .
git rebase --continue

# Push con force (solo en tu feature branch)
git push origin feature/HU01-catalogo-albumes --force-with-lease
```

### 4. Crear Pull Request

```bash
# Asegurar todo está pushed
git push origin feature/HU01-catalogo-albumes

# Crear PR en GitHub CLI
gh pr create \
  --base develop \
  --head feature/HU01-catalogo-albumes \
  --title "feat(albums): Implementar HU01 - Catálogo de álbumes" \
  --body "## Descripción
Implementación de la historia de usuario HU01 - Consultar catálogo de álbumes.

## Cambios
- AlbumsFragment con RecyclerView
- AlbumsViewModel con LiveData
- AlbumsRepository con caché
- Pruebas E2E en Espresso

## Checklist
- [x] Pruebas unitarias pasan
- [x] Pruebas E2E pasan
- [x] Probado en Android 5.0+
- [x] Sin warnings de Lint

Closes #1"
```

### 5. Después del Code Review

```bash
# Si hay cambios solicitados
git add .
git commit -m "fix(albums): aplicar cambios del code review"
git push origin feature/HU01-catalogo-albumes

# Merge se hace en GitHub con "Squash and merge"
```

### 6. Después del Merge

```bash
# Actualizar develop local
git checkout develop
git pull origin develop

# Eliminar rama local
git branch -d feature/HU01-catalogo-albumes

# Eliminar rama remota (opcional, GitHub lo hace automático)
git push origin --delete feature/HU01-catalogo-albumes
```

### 7. Crear Release

```bash
# Al final del sprint
git checkout develop
git pull origin develop

# Crear rama de release
git checkout -b release/sprint-1

# Hacer ajustes finales (versión, etc.)
# ... editar build.gradle versionName ...
git commit -m "chore: bump version to 1.0.0"

# Merge a main
git checkout main
git pull origin main
git merge release/sprint-1 --no-ff -m "Release Sprint 1"

# Crear tag
git tag -a v1.0.0 -m "Sprint 1 - HU01, HU02"

# Push main y tags
git push origin main
git push origin --tags

# Merge de vuelta a develop
git checkout develop
git merge release/sprint-1 --no-ff -m "Merge release/sprint-1 to develop"
git push origin develop

# Eliminar rama de release
git branch -d release/sprint-1
```

## Convenciones de Commits

### Formato

```
<tipo>(<alcance>): <descripción>

[cuerpo opcional]

[footer opcional]
```

### Tipos

| Tipo | Uso |
|------|-----|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de bug |
| `docs` | Documentación |
| `style` | Formato (no afecta lógica) |
| `refactor` | Refactorización |
| `test` | Pruebas |
| `chore` | Tareas de mantenimiento |

### Ejemplos

```bash
# Feature
git commit -m "feat(albums): implementar lista de álbumes con RecyclerView"

# Fix
git commit -m "fix(albums): corregir crash al cargar imagen nula"

# Test
git commit -m "test(albums): agregar pruebas E2E para HU01"

# Docs
git commit -m "docs(readme): actualizar instrucciones de instalación"

# Refactor
git commit -m "refactor(albums): extraer lógica a AlbumsRepository"

# Chore
git commit -m "chore(deps): actualizar Retrofit a 2.9.0"
```

## Template de PR

```markdown
## Descripción
Implementación de HU01 - Consultar catálogo de álbumes

## Tipo de cambio
- [x] Nueva funcionalidad (feat)
- [ ] Corrección de bug (fix)
- [ ] Refactorización (refactor)
- [ ] Documentación (docs)

## Cambios realizados
- Crear `AlbumsFragment` con RecyclerView
- Crear `AlbumsViewModel` con LiveData
- Crear `AlbumsRepository` con estrategia de caché
- Crear `AlbumServiceAdapter` para llamadas REST
- Agregar pruebas E2E en Espresso

## Screenshots
| Antes | Después |
|-------|---------|
| N/A | [screenshot] |

## Checklist
- [ ] Mi código sigue las convenciones del proyecto
- [ ] He revisado mi propio código
- [ ] He comentado código complejo
- [ ] He actualizado la documentación
- [ ] Mis cambios no generan warnings
- [ ] He agregado pruebas
- [ ] Las pruebas pasan localmente
- [ ] Los cambios dependen de PRs ya mergeados

## Issues relacionados
Closes #1
```

## Reglas Importantes

1. **NUNCA** hacer push directo a `main` o `develop`
2. **SIEMPRE** crear PR para integrar código
3. **SIEMPRE** usar "Squash and merge" en PRs
4. **SIEMPRE** tener al menos 1 review aprobado
5. **SIEMPRE** ejecutar pruebas antes de crear PR
6. **NUNCA** usar `--force` en `main` o `develop`
7. PRs deben referenciar el Issue/HU correspondiente
