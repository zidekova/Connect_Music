package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.PlaylistDao
import com.example.connectmusic.data.tables.Playlist
import kotlinx.coroutines.flow.Flow

class OfflinePlaylistRepository(private val playlistDao: PlaylistDao) : PlaylistRepository {
    override fun getAllPlaylistsStream(): Flow<List<Playlist>> = playlistDao.getAllPlaylists()
    override fun getPlaylistStream(id: Int): Flow<Playlist?> = playlistDao.getPlaylist(id)
    override suspend fun insertPlaylist(playlist: Playlist) = playlistDao.insert(playlist)
    override suspend fun deletePlaylist(playlist: Playlist) = playlistDao.delete(playlist)
}