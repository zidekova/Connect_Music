package com.example.connectmusic.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.connectmusic.ConnectMusicApplication
import com.example.connectmusic.ui.home.HomeViewModel
import com.example.connectmusic.ui.playlist.PlaylistEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
//        initializer {
//            ItemEditViewModel(
//                this.createSavedStateHandle(),
//                inventoryApplication().container.itemsRepository
//            )
//        }
        // Initializer for PlaylistEntryViewModel
        initializer {
            PlaylistEntryViewModel(connectMusicApplication().container.playlistRepository)
        }

        // Initializer for ItemDetailsViewModel
//        initializer {
//            ItemDetailsViewModel(
//                this.createSavedStateHandle(),
//                inventoryApplication().container.itemsRepository
//            )
//        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(connectMusicApplication().container.playlistRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [ConnectMusicApplication].
 */
fun CreationExtras.connectMusicApplication(): ConnectMusicApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ConnectMusicApplication)
