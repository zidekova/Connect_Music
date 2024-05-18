package com.example.connectmusic.data

import android.content.Context
import com.example.connectmusic.data.repositories.DecadeRepository
import com.example.connectmusic.data.repositories.GenreRepository
import com.example.connectmusic.data.repositories.InterpretRepository
import com.example.connectmusic.data.repositories.OfflineDecadeRepository
import com.example.connectmusic.data.repositories.OfflineGenreRepository
import com.example.connectmusic.data.repositories.OfflineInterpretRepository
import com.example.connectmusic.data.repositories.OfflinePlaylistRepository
import com.example.connectmusic.data.repositories.OfflineSongRepository
import com.example.connectmusic.data.repositories.PlaylistRepository
import com.example.connectmusic.data.repositories.SongRepository

interface AppContainer {
    val genreRepository: GenreRepository
    val decadeRepository: DecadeRepository
    val interpretRepository: InterpretRepository
    val songRepository: SongRepository
    val playlistRepository: PlaylistRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val genreRepository: GenreRepository by lazy {
        OfflineGenreRepository(AppDatabase.getDatabase(context).genreDao())
    }
    override val decadeRepository: DecadeRepository by lazy {
        OfflineDecadeRepository(AppDatabase.getDatabase(context).decadeDao())
    }
    override val interpretRepository: InterpretRepository by lazy {
        OfflineInterpretRepository(AppDatabase.getDatabase(context).interpretDao())
    }
    override val songRepository: SongRepository by lazy {
        OfflineSongRepository(AppDatabase.getDatabase(context).songDao())
    }
    override val playlistRepository: PlaylistRepository by lazy {
        OfflinePlaylistRepository(AppDatabase.getDatabase(context).playlistDao())
    }
}