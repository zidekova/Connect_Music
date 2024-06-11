package com.example.connectmusic.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.connectmusic.ConnectMusicApplication
import com.example.connectmusic.ui.home.HomeViewModel
import com.example.connectmusic.ui.playlist.PlaylistDetailsViewModel
import com.example.connectmusic.ui.playlist.NewPlaylistViewModel
import com.example.connectmusic.ui.search.SearchViewModel
import com.example.connectmusic.ui.song.SongViewModel

/**
 * Vytvorenie instancii ViewModelov pre celu aplikaciu
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer pre NewPlaylistViewModel
        initializer {
            NewPlaylistViewModel(connectMusicApplication().container.playlistRepository)
        }

        // Initializer pre PlaylistDetailsViewModel
        initializer {
            PlaylistDetailsViewModel(
                this.createSavedStateHandle(),
                connectMusicApplication().container.playlistRepository,
                connectMusicApplication().container.playlistSongRepository
            )
        }

        // Initializer pre HomeViewModel
        initializer {
            HomeViewModel(connectMusicApplication().container.playlistRepository)
        }

        // Initializer pre SearchViewModel
        initializer {
            SearchViewModel(
                connectMusicApplication().container.genreRepository,
                connectMusicApplication().container.interpretRepository,
                connectMusicApplication().container.decadeRepository,
                connectMusicApplication().container.songRepository
            )
        }

        // Initializer pre SongViewModel
        initializer {
            SongViewModel(
                connectMusicApplication().container.songRepository,
                connectMusicApplication().container.playlistRepository,
                connectMusicApplication().container.playlistSongRepository
            )
        }
    }
}

fun CreationExtras.connectMusicApplication(): ConnectMusicApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ConnectMusicApplication)
