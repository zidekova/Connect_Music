package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectmusic.data.tables.PlaylistSong
import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistSongDao {
    ///////////////// UPRAVIT //////////////////////
    @Query("SELECT * from playlist_song")
    fun getAllPlaylistSongs(): Flow<List<PlaylistSong>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(playlistSong: PlaylistSong)

    @Update
    suspend fun update(playlistSong: PlaylistSong)

    @Delete
    suspend fun delete(playlistSong: PlaylistSong)
}