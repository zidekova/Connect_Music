package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectmusic.data.tables.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Query("SELECT * from genre ORDER BY name_genre ASC")
    fun getAllGenres(): Flow<List<Genre>>

    @Query("SELECT name_genre from genre ORDER BY name_genre ASC")
    fun getAllGenresNames(): Flow<List<String>>

    @Query("SELECT * from genre WHERE id_genre = :id")
    fun getGenre(id: Int): Flow<Genre>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genre: Genre)

    @Update
    suspend fun update(genre: Genre)

    @Delete
    suspend fun delete(genre: Genre)
}