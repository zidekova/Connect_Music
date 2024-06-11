package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.PlaylistSong

interface PlaylistSongRepository {
    fun getAllPlaylistSongsNames(idPlaylist: Int): List<String>
    fun getIdSongByName(nameSong: String): Int
    suspend fun insertPlaylistSong(playlistSong: PlaylistSong)
    suspend fun deletePlaylistSongFromPlaylist(idPlaylist: Int)
}