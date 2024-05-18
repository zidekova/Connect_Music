package com.example.connectmusic.data.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectmusic.data.tables.Decade
import kotlinx.coroutines.flow.Flow

interface DecadeDao {
    @Query("SELECT * from decade ORDER BY nameDecade ASC")
    fun getAllDecades(): Flow<List<Decade>>

    @Query("SELECT * from decade WHERE idDecade = :id")
    fun getDecade(id: Int): Flow<Decade>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(decade: Decade)

    @Update
    suspend fun update(decade: Decade)

    @Delete
    suspend fun delete(decade: Decade)
}