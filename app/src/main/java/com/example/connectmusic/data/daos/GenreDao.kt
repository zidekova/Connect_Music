package com.example.connectmusic.data.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectmusic.data.tables.Genre
import kotlinx.coroutines.flow.Flow

interface GenreDao {
    @Query("SELECT * from genre ORDER BY nameGenre ASC")
    fun getAllGenres(): Flow<List<Genre>>

    @Query("SELECT * from genre WHERE idGenre = :id")
    fun getGenre(id: Int): Flow<Genre>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genre: Genre)

    @Update
    suspend fun update(genre: Genre)

    @Delete
    suspend fun delete(genre: Genre)
}