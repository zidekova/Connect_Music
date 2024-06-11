package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getAllPlaylistsStream(): Flow<List<Playlist>>
    fun getPlaylistStream(id: Int): Flow<Playlist?>
    suspend fun insertPlaylist(playlist: Playlist)
    suspend fun deletePlaylist(playlist: Playlist)
}