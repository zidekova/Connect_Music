package com.example.connectmusic.data

import android.content.Context
import com.example.connectmusic.data.repositories.DecadesRepository
import com.example.connectmusic.data.repositories.GenreRepository
import com.example.connectmusic.data.repositories.InterpretRepository
import com.example.connectmusic.data.repositories.OfflineDecadeRepository
import com.example.connectmusic.data.repositories.OfflineGenreRepository
import com.example.connectmusic.data.repositories.OfflineInterpretRepository
import com.example.connectmusic.data.repositories.OfflinePlaylistRepository
import com.example.connectmusic.data.repositories.OfflinePlaylistSongRepository
import com.example.connectmusic.data.repositories.OfflineSongRepository
import com.example.connectmusic.data.repositories.PlaylistRepository
import com.example.connectmusic.data.repositories.PlaylistSongRepository
import com.example.connectmusic.data.repositories.SongRepository

interface AppContainer {
    val genreRepository: GenreRepository
    val decadeRepository: DecadesRepository
    val interpretRepository: InterpretRepository
    val songRepository: SongRepository
    val playlistRepository: PlaylistRepository
    val playlistSongRepository: PlaylistSongRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val genreRepository: GenreRepository by lazy {
        OfflineGenreRepository(AppDatabase.getDatabase(context).genreDao())
    }
    override val decadeRepository: DecadesRepository by lazy {
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
    override val playlistSongRepository: PlaylistSongRepository by lazy {
        OfflinePlaylistSongRepository(AppDatabase.getDatabase(context).playlistSongDao())
    }
}