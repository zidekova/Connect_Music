package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.connectmusic.data.tables.Decade
import kotlinx.coroutines.flow.Flow

@Dao
interface DecadeDao {
    @Query("SELECT * from decade ORDER BY name_decade ASC")
    fun getAllDecades(): Flow<List<Decade>>

    @Query("SELECT name_decade from decade")
    fun getAllDecadesNames(): Flow<List<String>>

    @Query("SELECT * from decade WHERE id_decade = :id")
    fun getDecade(id: Int): Flow<Decade>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(decade: Decade)

    @Update
    suspend fun update(decade: Decade)

    @Delete
    suspend fun delete(decade: Decade)
}