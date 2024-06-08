package com.example.connectmusic.ui.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectmusic.data.repositories.PlaylistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlaylistDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val playlistRepository: PlaylistRepository,
) : ViewModel() {

    private val playlistId: Int = checkNotNull(savedStateHandle[PlaylistDetailsDestination.playlistIdArg])

    val uiState: StateFlow<PlaylistDetailsUiState> =
        playlistRepository.getPlaylistStream(playlistId)
            .filterNotNull()
            .map {
                PlaylistDetailsUiState(playlistDetails = it.toPlaylistDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PlaylistDetailsUiState()
            )

    suspend fun deletePlaylist() {
        playlistRepository.deletePlaylist(uiState.value.playlistDetails.toPlaylist())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class PlaylistDetailsUiState(
    val playlistDetails: PlaylistDetails = PlaylistDetails()
)