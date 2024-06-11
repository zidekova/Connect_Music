package com.example.connectmusic.ui.song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectmusic.data.repositories.PlaylistRepository
import com.example.connectmusic.data.repositories.PlaylistSongRepository
import com.example.connectmusic.data.repositories.SongRepository
import com.example.connectmusic.data.tables.Playlist
import com.example.connectmusic.data.tables.PlaylistSong
import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * View model pre SongScreeny.
 * Ziskava zoznam playlistov, skladieb a skladieb v playliste z databazy.
 */
class SongViewModel(
    private val songRepository: SongRepository,
    private val playlistRepository: PlaylistRepository,
    private val playlistSongRepository: PlaylistSongRepository
) : ViewModel() {

    private val _data = MutableStateFlow<List<String>>(emptyList())
    val data: StateFlow<List<String>> = _data

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists

    init {
        loadPlaylists()
    }

    fun loadPlaylists() {
        viewModelScope.launch {
            playlistRepository.getAllPlaylistsStream().collect {
                _playlists.value = it
            }
        }
    }

    suspend fun getSongById(id: Int): Song? {
        return withContext(Dispatchers.IO) {
            songRepository.getSongById(id)
        }
    }

    suspend fun getInterpretBySongId(id: Int): String {
        return withContext(Dispatchers.IO) {
            songRepository.getInterpretBySongId(id)
        }
    }

    suspend fun getGenreBySongId(id: Int): String? {
        return withContext(Dispatchers.IO) {
            songRepository.getGenreBySongId(id)
        }
    }

    suspend fun getDecadeBySongId(id: Int): String? {
        return withContext(Dispatchers.IO) {
            songRepository.getDecadeBySongId(id)
        }
    }

    suspend fun addSongToPlaylist(songId: Int, playlistId: Int) {
        val playlistSong = PlaylistSong(
            id_playlist = playlistId,
            id_song = songId
        )
        playlistSongRepository.insertPlaylistSong(playlistSong)
    }

    suspend fun getSongNameById(id: Int): String {
        return withContext(Dispatchers.IO) {
            songRepository.getSongName(id)
        }
    }
}


