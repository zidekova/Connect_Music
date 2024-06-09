package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.PlaylistSong
import kotlinx.coroutines.flow.Flow

interface PlaylistSongRepository {
    fun getAllPlaylistSongsStream(): Flow<List<PlaylistSong>>
    fun getPlaylistSongStream(id: Int): Flow<PlaylistSong?>
    fun getAllPlaylistSongsNames(idPlaylist: Int): List<String>
    fun getAllPlaylistSongs(idPlaylist: Int): List<PlaylistSong>
    fun getIdSongByName(nameSong: String): Int
    suspend fun insertPlaylistSong(playlistSong: PlaylistSong)
    suspend fun deletePlaylistSong(playlistSong: PlaylistSong)
    suspend fun deletePlaylistSongFromPlaylist(idPlaylist: Int)
    suspend fun deleteSongFromPlaylist(idPlaylist: Int, idSong: Int)
    suspend fun updatePlaylistSong(playlistSong: PlaylistSong)
}