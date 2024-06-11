package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.PlaylistSongDao
import com.example.connectmusic.data.tables.PlaylistSong

class OfflinePlaylistSongRepository(private val playlistSongDao: PlaylistSongDao) : PlaylistSongRepository {
    override fun getAllPlaylistSongsNames(idPlaylist: Int): List<String> = playlistSongDao.getAllPlaylistSongsNames(idPlaylist)
    override fun getIdSongByName(nameSong: String): Int = playlistSongDao.getIdSongByName(nameSong)
    override suspend fun insertPlaylistSong(playlistSong: PlaylistSong) = playlistSongDao.insert(playlistSong)
    override suspend fun deletePlaylistSongFromPlaylist(idPlaylist: Int) = playlistSongDao.deletePlaylistSongFromPlaylist(idPlaylist)
}