package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InterpretDao {

    @Query("SELECT name_interpret from interpret ORDER BY name_interpret ASC")
    fun getAllInterpretsNames(): Flow<List<String>>
}