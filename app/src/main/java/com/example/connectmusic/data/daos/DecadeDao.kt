package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DecadeDao {
    @Query("SELECT name_decade from decade")
    fun getAllDecadesNames(): Flow<List<String>>
}