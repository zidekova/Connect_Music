package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("SELECT * from song ORDER BY name_song ASC")
    fun getAllSongs(): Flow<List<Song>>

    @Query("SELECT * from song WHERE id_song = :id")
    fun getSong(id: Int): Flow<Song>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: Song)

    @Update
    suspend fun update(song: Song)

    @Delete
    suspend fun delete(song: Song)
}