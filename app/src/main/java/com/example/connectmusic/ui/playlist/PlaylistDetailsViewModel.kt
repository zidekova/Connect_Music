package com.example.connectmusic.ui.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectmusic.data.repositories.PlaylistRepository
import com.example.connectmusic.data.repositories.PlaylistSongRepository
import com.example.connectmusic.data.repositories.SongRepository
import com.example.connectmusic.data.tables.Playlist
import com.example.connectmusic.data.tables.PlaylistSong
import com.example.connectmusic.data.tables.Song
import com.example.connectmusic.ui.home.HomeUiState
import com.example.connectmusic.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val playlistRepository: PlaylistRepository,
    private val playlistSongRepository: PlaylistSongRepository
) : ViewModel() {

    val playlistId: Int = checkNotNull(savedStateHandle[PlaylistDetailsDestination.playlistIdArg])

    private val _uiState = MutableStateFlow<PlaylistDetailsUiState>(PlaylistDetailsUiState())

    val uiState: StateFlow<PlaylistDetailsUiState> =
        playlistRepository.getPlaylistStream(playlistId)
            .filterNotNull()
            .flatMapLatest { playlistDetails ->
                playlistSongRepository.getAllPlaylistSongsStream()
                    .map { songsList ->
                        PlaylistDetailsUiState(
                            playlistDetails = playlistDetails.toPlaylistDetails(),
                            songsList = songsList ?: emptyList()
                        )
                    }
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

    suspend fun deleteSongFromPlaylist(songId: Int) {
        withContext(Dispatchers.IO) {
            playlistSongRepository.deleteSongFromPlaylist(songId, playlistId)
            // Aktualizácia zoznamu piesní po vymazaní
            val updatedSongsList = playlistSongRepository.getAllPlaylistSongs(playlistId)
            _uiState.value = uiState.value.copy(songsList = updatedSongsList ?: emptyList())
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

data class PlaylistDetailsUiState(
    val playlistDetails: PlaylistDetails = PlaylistDetails(),
    val songsList: List<PlaylistSong> = listOf()
)