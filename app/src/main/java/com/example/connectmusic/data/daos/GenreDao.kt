package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Query("SELECT name_genre from genre ORDER BY name_genre ASC")
    fun getAllGenresNames(): Flow<List<String>>
}