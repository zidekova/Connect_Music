package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.InterpretDao
import kotlinx.coroutines.flow.Flow

class OfflineInterpretRepository(private val interpretDao: InterpretDao) : InterpretRepository {
    override fun getAllInterpretsNames(): Flow<List<String>> = interpretDao.getAllInterpretsNames()
}