package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.connectmusic.data.tables.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * from playlist ORDER BY name_playlist ASC")
    fun getAllPlaylists(): Flow<List<Playlist>>
    @Query("SELECT * from playlist WHERE id_playlist = :id")
    fun getPlaylist(id: Int): Flow<Playlist>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(playlist: Playlist)
    @Delete
    suspend fun delete(playlist: Playlist)
}