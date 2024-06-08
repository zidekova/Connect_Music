package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.Decade
import com.example.connectmusic.data.tables.Genre
import kotlinx.coroutines.flow.Flow

interface DecadesRepository {
    fun getAllDecadesStream(): Flow<List<Decade>>
    fun getAllDecadesNames(): Flow<List<String>>
    fun getDecadeStream(id: Int): Flow<Decade?>
    suspend fun insertDecade(decade: Decade)
    suspend fun deleteDecade(decade: Decade)
    suspend fun updateDecade(decade: Decade)
}