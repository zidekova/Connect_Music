package com.example.connectmusic.ui.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectmusic.data.repositories.PlaylistRepository
import com.example.connectmusic.data.repositories.PlaylistSongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

/**
 * View model pre PlaylistDetailsScreen.
 * Ziskava zoznam playlistov z databazy a zoznam pesniciek v playliste.
 */
class PlaylistDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val playlistRepository: PlaylistRepository,
    private val playlistSongRepository: PlaylistSongRepository
) : ViewModel() {

    val playlistId: Int = checkNotNull(savedStateHandle[PlaylistDetailsDestination.playlistIdArg])

    val uiState: StateFlow<PlaylistDetailsUiState> =
        playlistRepository.getPlaylistStream(playlistId)
            .filterNotNull()
            .map{ playlistDetails ->
                PlaylistDetailsUiState(
                    playlistDetails = playlistDetails.toPlaylistDetails()
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PlaylistDetailsUiState()
            )

    suspend fun deletePlaylist() {
        withContext(Dispatchers.IO) {
            playlistSongRepository.deletePlaylistSongFromPlaylist(playlistId)
            playlistRepository.deletePlaylist(uiState.value.playlistDetails.toPlaylist())
        }
    }

    suspend fun getPlaylistSong(idPlaylist: Int): List<String>? {
        return withContext(Dispatchers.IO) {
            playlistSongRepository.getAllPlaylistSongsNames(idPlaylist)
        }
    }

    suspend fun getIdSongFromName(nameSong: String): Int {
        return withContext(Dispatchers.IO) {
            playlistSongRepository.getIdSongByName(nameSong)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State pre PlaylistDetails.
 */
data class PlaylistDetailsUiState(
    val playlistDetails: PlaylistDetails = PlaylistDetails()
)