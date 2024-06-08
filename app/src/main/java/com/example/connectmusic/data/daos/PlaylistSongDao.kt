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
    ///////////////// UPRAVIT //////////////////////
    @Query("SELECT * from playlist_song")
    fun getAllPlaylistSongs(): Flow<List<PlaylistSong>>
    @Query("SELECT * from playlist_song WHERE id_playlist_song = :id")
    fun getPlaylistSong(id: Int): Flow<PlaylistSong>
    @Query("SELECT name_song from song JOIN playlist_song USING(id_song) WHERE id_playlist = :idPlaylist")
    fun getAllPlaylistSongsNames(idPlaylist: Int): List<String>

    @Query("DELETE from playlist_song WHERE id_playlist = :idPlaylist")
    suspend fun deletePlaylistSongFromPlaylist(idPlaylist: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(playlistSong: PlaylistSong)

    @Update
    suspend fun update(playlistSong: PlaylistSong)

    @Delete
    suspend fun delete(playlistSong: PlaylistSong)
}