---
name: api-rest-android
description: Consumir APIs REST desde Android con Retrofit y Kotlin Coroutines. Usa este skill cuando necesites hacer llamadas HTTP, configurar Retrofit, crear Service Adapters, o manejar respuestas JSON. Aplica a Retrofit, OkHttp, Gson, Moshi, APIs REST.
---

# API REST en Android

## Configuración de Retrofit

### 1. Dependencias

```kotlin
// build.gradle (app)
dependencies {
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    
    // OkHttp (logging interceptor)
    implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'
    
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
}
```

### 2. Permisos en Manifest

```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.INTERNET" />

<application
    android:usesCleartextTraffic="true"
    ... >
```

### 3. Network Config (Android 9+)

```xml
<!-- res/xml/network_security_config.xml -->
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">10.0.2.2</domain>
        <domain includeSubdomains="true">localhost</domain>
    </domain-config>
</network-security-config>
```

```xml
<!-- AndroidManifest.xml -->
<application
    android:networkSecurityConfig="@xml/network_security_config"
    ... >
```

## Implementación

### 1. DTOs (Data Transfer Objects)

```kotlin
// data/remote/dto/AlbumDto.kt
data class AlbumDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("description") val description: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("recordLabel") val recordLabel: String,
    @SerializedName("tracks") val tracks: List<TrackDto>? = null,
    @SerializedName("performers") val performers: List<PerformerDto>? = null,
    @SerializedName("comments") val comments: List<CommentDto>? = null
)

data class TrackDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("duration") val duration: String
)
```

### 2. API Interface

```kotlin
// data/remote/api/VinilosApi.kt
interface VinilosApi {
    
    // Albums
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>
    
    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") id: Int): AlbumDto
    
    @POST("albums")
    suspend fun createAlbum(@Body album: AlbumDto): AlbumDto
    
    @POST("albums/{id}/tracks")
    suspend fun addTrack(
        @Path("id") albumId: Int,
        @Body track: TrackDto
    ): TrackDto
    
    // Musicians
    @GET("musicians")
    suspend fun getMusicians(): List<MusicianDto>
    
    @GET("musicians/{id}")
    suspend fun getMusician(@Path("id") id: Int): MusicianDto
    
    // Collectors
    @GET("collectors")
    suspend fun getCollectors(): List<CollectorDto>
    
    @GET("collectors/{id}")
    suspend fun getCollector(@Path("id") id: Int): CollectorDto
}
```

### 3. Retrofit Client (Singleton)

```kotlin
// data/remote/RetrofitClient.kt
object RetrofitClient {
    
    private const val BASE_URL = "https://backvynils-q6yc.onrender.com/"
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val api: VinilosApi = retrofit.create(VinilosApi::class.java)
}
```

### 4. Service Adapter

```kotlin
// data/remote/adapter/AlbumServiceAdapter.kt
class AlbumServiceAdapter(
    private val api: VinilosApi = RetrofitClient.api
) {
    suspend fun getAlbums(): List<Album> {
        return api.getAlbums().map { it.toModel() }
    }
    
    suspend fun getAlbum(id: Int): Album {
        return api.getAlbum(id).toModel()
    }
    
    suspend fun createAlbum(album: Album): Album {
        val dto = AlbumDto(
            id = 0,
            name = album.name,
            cover = album.cover,
            releaseDate = album.releaseDate,
            description = album.description,
            genre = album.genre,
            recordLabel = album.recordLabel
        )
        return api.createAlbum(dto).toModel()
    }
    
    suspend fun addTrack(albumId: Int, track: Track): Track {
        val dto = TrackDto(
            id = 0,
            name = track.name,
            duration = track.duration
        )
        return api.addTrack(albumId, dto).toModel()
    }
}
```

### 5. Mappers

```kotlin
// data/remote/dto/Mappers.kt
fun AlbumDto.toModel() = Album(
    id = id,
    name = name,
    cover = cover,
    releaseDate = releaseDate,
    description = description,
    genre = genre,
    recordLabel = recordLabel,
    tracks = tracks?.map { it.toModel() } ?: emptyList()
)

fun TrackDto.toModel() = Track(
    id = id,
    name = name,
    duration = duration
)
```

## Manejo de Errores

```kotlin
// util/NetworkResult.kt
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int, val message: String) : NetworkResult<Nothing>()
    data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}

// En ServiceAdapter
suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (e: HttpException) {
        NetworkResult.Error(e.code(), e.message())
    } catch (e: IOException) {
        NetworkResult.Exception(e)
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    }
}
```

## Carga de Imágenes con Glide/Coil

```kotlin
// build.gradle
implementation 'io.coil-kt:coil:2.5.0'

// En Fragment/Adapter
binding.imageAlbumCover.load(album.cover) {
    crossfade(true)
    placeholder(R.drawable.placeholder_album)
    error(R.drawable.error_image)
}
```

## Backend Vinilos - Endpoints

| Método | Endpoint | Body | Response |
|--------|----------|------|----------|
| GET | `/albums` | - | `List<AlbumDto>` |
| GET | `/albums/{id}` | - | `AlbumDto` |
| POST | `/albums` | `AlbumDto` | `AlbumDto` |
| POST | `/albums/{id}/tracks` | `TrackDto` | `TrackDto` |
| GET | `/musicians` | - | `List<MusicianDto>` |
| GET | `/musicians/{id}` | - | `MusicianDto` |
| GET | `/collectors` | - | `List<CollectorDto>` |
| GET | `/collectors/{id}` | - | `CollectorDto` |
