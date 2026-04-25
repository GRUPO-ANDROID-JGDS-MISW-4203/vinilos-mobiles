---
name: mvvm-architecture
description: Implementar arquitectura MVVM con Jetpack Components en Android. Usa este skill cuando necesites crear ViewModels, LiveData, Repositories, DAOs, o estructurar código siguiendo MVVM. Aplica a Android, Kotlin, Jetpack, Room, ViewModel, LiveData.
---

# MVVM Architecture Skill

## Componentes de Arquitectura

### 1. UIState - Estados de UI

```kotlin
// util/UIState.kt
sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}
```

### 2. ViewModel Base

```kotlin
// ui/base/BaseViewModel.kt
abstract class BaseViewModel<T> : ViewModel() {
    
    protected val _state = MutableLiveData<UIState<T>>()
    val state: LiveData<UIState<T>> = _state
    
    protected fun loading() {
        _state.value = UIState.Loading
    }
    
    protected fun success(data: T) {
        _state.value = UIState.Success(data)
    }
    
    protected fun error(message: String) {
        _state.value = UIState.Error(message)
    }
    
    protected fun launchDataLoad(block: suspend () -> T) {
        viewModelScope.launch {
            loading()
            try {
                success(block())
            } catch (e: Exception) {
                error(e.message ?: "Error desconocido")
            }
        }
    }
}
```

### 3. ViewModel Específico

```kotlin
// ui/albums/AlbumsViewModel.kt
class AlbumsViewModel(
    private val repository: AlbumsRepository
) : BaseViewModel<List<Album>>() {
    
    fun loadAlbums() = launchDataLoad {
        repository.getAlbums()
    }
    
    fun refreshAlbums() = launchDataLoad {
        repository.refreshAlbums()
    }
}
```

### 4. ViewModelFactory

```kotlin
// ui/albums/AlbumsViewModelFactory.kt
class AlbumsViewModelFactory(
    private val repository: AlbumsRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumsViewModel::class.java)) {
            return AlbumsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

### 5. Repository Pattern

```kotlin
// data/repository/AlbumsRepository.kt
class AlbumsRepository(
    private val remoteDataSource: AlbumServiceAdapter,
    private val localDataSource: AlbumDao
) {
    // Estrategia: Network-first con fallback a cache
    suspend fun getAlbums(): List<Album> {
        return try {
            val remote = remoteDataSource.getAlbums()
            localDataSource.insertAll(remote.map { it.toEntity() })
            remote
        } catch (e: Exception) {
            localDataSource.getAll().map { it.toModel() }
        }
    }
    
    // Forzar refresh desde red
    suspend fun refreshAlbums(): List<Album> {
        val remote = remoteDataSource.getAlbums()
        localDataSource.deleteAll()
        localDataSource.insertAll(remote.map { it.toEntity() })
        return remote
    }
}
```

### 6. Room DAO

```kotlin
// data/local/dao/AlbumDao.kt
@Dao
interface AlbumDao {
    
    @Query("SELECT * FROM albums ORDER BY name ASC")
    suspend fun getAll(): List<AlbumEntity>
    
    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getById(id: Int): AlbumEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: AlbumEntity)
    
    @Query("DELETE FROM albums")
    suspend fun deleteAll()
}
```

### 7. Room Entity

```kotlin
// data/local/entity/AlbumEntity.kt
@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cover") val cover: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "record_label") val recordLabel: String
)

// Mappers
fun AlbumEntity.toModel() = Album(id, name, cover, releaseDate, description, genre, recordLabel)
fun Album.toEntity() = AlbumEntity(id, name, cover, releaseDate, description, genre, recordLabel)
```

### 8. Room Database

```kotlin
// data/local/database/VinilosDatabase.kt
@Database(
    entities = [AlbumEntity::class, ArtistEntity::class, CollectorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class VinilosDatabase : RoomDatabase() {
    
    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao
    abstract fun collectorDao(): CollectorDao
    
    companion object {
        @Volatile
        private var INSTANCE: VinilosDatabase? = null
        
        fun getDatabase(context: Context): VinilosDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    VinilosDatabase::class.java,
                    "vinilos_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
```

## Flujo de Datos Completo

```
┌─────────────────────────────────────────────────────────────────┐
│  Fragment                                                       │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ viewModel.state.observe(viewLifecycleOwner) { state ->  │   │
│  │     when (state) {                                       │   │
│  │         Loading -> binding.progress.visible()            │   │
│  │         Success -> adapter.submitList(state.data)        │   │
│  │         Error   -> showSnackbar(state.message)           │   │
│  │     }                                                    │   │
│  │ }                                                        │   │
│  │ viewModel.loadAlbums()                                   │   │
│  └─────────────────────────────────────────────────────────┘   │
│                              │                                  │
│                              ▼                                  │
│  ViewModel                                                      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ fun loadAlbums() = viewModelScope.launch {              │   │
│  │     _state.value = Loading                               │   │
│  │     _state.value = Success(repository.getAlbums())       │   │
│  │ }                                                        │   │
│  └─────────────────────────────────────────────────────────┘   │
│                              │                                  │
│                              ▼                                  │
│  Repository                                                     │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ try { remoteDataSource.getAlbums() }                    │   │
│  │ catch { localDataSource.getAll() }                       │   │
│  └─────────────────────────────────────────────────────────┘   │
│              │                          │                       │
│              ▼                          ▼                       │
│  ┌──────────────────┐      ┌──────────────────────┐            │
│  │ ServiceAdapter   │      │        DAO           │            │
│  │ (Retrofit)       │      │       (Room)         │            │
│  └──────────────────┘      └──────────────────────┘            │
└─────────────────────────────────────────────────────────────────┘
```

## Dependencias Gradle

```kotlin
// build.gradle (app)
dependencies {
    // ViewModel y LiveData
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.7.0"
    
    // Room
    implementation "androidx.room:room-runtime:2.6.1"
    implementation "androidx.room:room-ktx:2.6.1"
    kapt "androidx.room:room-compiler:2.6.1"
    
    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
}
```
