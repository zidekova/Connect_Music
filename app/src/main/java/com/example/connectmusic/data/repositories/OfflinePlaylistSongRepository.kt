package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.PlaylistSongDao
import com.example.connectmusic.data.tables.PlaylistSong
import kotlinx.coroutines.flow.Flow

class OfflinePlaylistSongRepository(private val playlistSongDao: PlaylistSongDao) : PlaylistSongRepository {
    override fun getAllPlaylistSongsStream(): Flow<List<PlaylistSong>> = playlistSongDao.getAllPlaylistSongs()

    override fun getPlaylistSongStream(id: Int): Flow<PlaylistSong?> = playlistSongDao.getPlaylistSong(id)
    override fun getAllPlaylistSongsNames(idPlaylist: Int): List<String> = playlistSongDao.getAllPlaylistSongsNames(idPlaylist)
    override fun getAllPlaylistSongs(idPlaylist: Int): List<PlaylistSong> = playlistSongDao.getAllPlaylistSongs(idPlaylist)

    override fun getIdSongByName(nameSong: String): Int = playlistSongDao.getIdSongByName(nameSong)

    override suspend fun insertPlaylistSong(playlistSong: PlaylistSong) = playlistSongDao.insert(playlistSong)

    override suspend fun deletePlaylistSong(playlistSong: PlaylistSong) = playlistSongDao.delete(playlistSong)
    override suspend fun deletePlaylistSongFromPlaylist(idPlaylist: Int) = playlistSongDao.deletePlaylistSongFromPlaylist(idPlaylist)
    override suspend fun deleteSongFromPlaylist(idPlaylist: Int, idSong: Int) = playlistSongDao.deleteSongFromPlaylist(idPlaylist, idSong)

    override suspend fun updatePlaylistSong(playlistSong: PlaylistSong) = playlistSongDao.update(playlistSong)
}