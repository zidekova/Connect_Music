package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectmusic.data.tables.Playlist
import com.example.connectmusic.data.tables.PlaylistSong
import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistSongDao {
    @Query("SELECT name_song from song JOIN playlist_song USING(id_song) WHERE id_playlist = :idPlaylist")
    fun getAllPlaylistSongsNames(idPlaylist: Int): List<String>
    @Query("SELECT id_song from playlist_song JOIN song USING(id_song) WHERE name_song = :nameSong")
    fun getIdSongByName(nameSong: String): Int
    @Query("DELETE from playlist_song WHERE id_playlist = :idPlaylist")
    suspend fun deletePlaylistSongFromPlaylist(idPlaylist: Int)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(playlistSong: PlaylistSong)

}