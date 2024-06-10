package com.example.connectmusic.ui.playlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.connectmusic.data.repositories.PlaylistRepository
import com.example.connectmusic.data.tables.Playlist

/**
 * View model pre NewPlaylistScreen.
 * Ziskava zoznam playlistov z databazy.
 */
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

/**
 * Ui State pre Playlist.
 */
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
    id_playlist = idPlaylist,
    name_playlist = namePlaylist
)

fun Playlist.toPlaylistDetails(): PlaylistDetails = PlaylistDetails(
    idPlaylist = id_playlist,
    namePlaylist = name_playlist
)