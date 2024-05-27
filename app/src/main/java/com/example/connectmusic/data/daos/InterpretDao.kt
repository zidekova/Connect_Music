package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectmusic.data.tables.Interpret
import kotlinx.coroutines.flow.Flow

@Dao
interface InterpretDao {
    @Query("SELECT * from interpret ORDER BY nameInterpret ASC")
    fun getAllInterprets(): Flow<List<Interpret>>

    @Query("SELECT * from interpret WHERE idInterpret = :id")
    fun getInterpret(id: Int): Flow<Interpret>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(interpret: Interpret)

    @Update
    suspend fun update(interpret: Interpret)

    @Delete
    suspend fun delete(interpret: Interpret)
}