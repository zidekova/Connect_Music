package com.example.connectmusic.ui.playlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.connectmusic.data.repositories.PlaylistRepository
import com.example.connectmusic.data.tables.Playlist

class PlaylistEntryViewModel(private val playlistRepository: PlaylistRepository) : ViewModel() {

    var playlistUiState by mutableStateOf(PlaylistUiState())
        private set

    fun updateUiState(playlistDetails: PlaylistDetails) {
        playlistUiState =
            PlaylistUiState(playlistDetails = playlistDetails, isEntryValid = validateInput(playlistDetails))
    }

    suspend fun savePlaylist() {
        if (validateInput()) {
            playlistRepository.insertPlaylist(playlistUiState.playlistDetails.toPlaylist())
        }
    }

    private fun validateInput(uiState: PlaylistDetails = playlistUiState.playlistDetails): Boolean {
        return with(uiState) {
            namePlaylist.isNotBlank() && idPlaylist.toString().isNotBlank() && idSong.toString().isNotBlank()
        }
    }
}

data class PlaylistUiState(
    val playlistDetails: PlaylistDetails = PlaylistDetails(),
    val isEntryValid: Boolean = false
)

data class PlaylistDetails(
    val idPlaylist: Int = 0,
    val namePlaylist: String = "",
    val idSong: Int = 0
)

fun PlaylistDetails.toPlaylist(): Playlist = Playlist(
    idPlaylist = idPlaylist,
    namePlaylist = namePlaylist,
    idSong = idSong
)

fun Playlist.toPlayslistUiState(isEntryValid: Boolean = false): PlaylistUiState = PlaylistUiState(
    playlistDetails = this.toPlaylistDetails(),
    isEntryValid = isEntryValid
)

fun Playlist.toPlaylistDetails(): PlaylistDetails = PlaylistDetails(
    idPlaylist = idPlaylist,
    namePlaylist = namePlaylist,
    idSong = idSong
)