package com.example.connectmusic.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectmusic.data.repositories.PlaylistRepository
import com.example.connectmusic.data.tables.Playlist
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(playlistRepository: PlaylistRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        playlistRepository.getAllPlaylistsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val playlistsList: List<Playlist> = listOf()
)
