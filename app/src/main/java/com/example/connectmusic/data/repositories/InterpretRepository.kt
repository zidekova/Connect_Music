package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.Interpret
import kotlinx.coroutines.flow.Flow

interface InterpretRepository {
    fun getAllInterpretsStream(): Flow<List<Interpret>>
    fun getAllInterpretsNames(): Flow<List<String>>
    fun getInterpretStream(id: Int): Flow<Interpret?>
    suspend fun insertInterpret(interpret: Interpret)
    suspend fun deleteInterpret(interpret: Interpret)
    suspend fun updateInterpret(interpret: Interpret)
}