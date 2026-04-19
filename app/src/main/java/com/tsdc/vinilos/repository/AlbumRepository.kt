package com.tsdc.vinilos.repository

import com.tsdc.vinilos.model.Album
import com.tsdc.vinilos.model.Performer
import com.tsdc.vinilos.model.Track

class AlbumRepository {

    private val albums = listOf(
        Album(
            id = 1,
            name = "Buscando América",
            cover = "https://picsum.photos/600/400?random=1",
            releaseDate = "1984-01-01",
            description = "Álbum representativo de Rubén Blades con contenido social.",
            genre = "Salsa",
            recordLabel = "Fania",
            performers = listOf(Performer(1, "Rubén Blades")),
            tracks = listOf(
                Track(1, "Decisiones", "4:30"),
                Track(2, "El padre Antonio", "5:10"),
                Track(3, "Buscando América", "4:45")
            )
        ),
        Album(
            id = 2,
            name = "A Night at the Opera",
            cover = "https://picsum.photos/600/400?random=2",
            releaseDate = "1975-01-01",
            description = "Uno de los álbumes más emblemáticos de Queen.",
            genre = "Rock",
            recordLabel = "EMI",
            performers = listOf(Performer(2, "Queen")),
            tracks = listOf(
                Track(1, "Bohemian Rhapsody", "5:55"),
                Track(2, "Love of My Life", "3:38"),
                Track(3, "You're My Best Friend", "2:50")
            )
        ),
        Album(
            id = 3,
            name = "A Day at the Races",
            cover = "https://picsum.photos/600/400?random=3",
            releaseDate = "1976-01-01",
            description = "Continuación del sonido clásico de Queen.",
            genre = "Rock",
            recordLabel = "EMI",
            performers = listOf(Performer(2, "Queen")),
            tracks = listOf(
                Track(1, "Somebody to Love", "4:56"),
                Track(2, "Tie Your Mother Down", "4:47")
            )
        )
    )

    suspend fun getAlbums(): List<Album> = albums

    suspend fun getAlbum(id: Int): Album = albums.first { it.id == id }
}