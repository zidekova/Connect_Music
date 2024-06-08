package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.InterpretDao
import com.example.connectmusic.data.tables.Interpret
import kotlinx.coroutines.flow.Flow

class OfflineInterpretRepository(private val interpretDao: InterpretDao) : InterpretRepository {
    override fun getAllInterpretsStream(): Flow<List<Interpret>> = interpretDao.getAllInterprets()
    override fun getAllInterpretsNames(): Flow<List<String>> = interpretDao.getAllInterpretsNames()

    override fun getInterpretStream(id: Int): Flow<Interpret?> = interpretDao.getInterpret(id)

    override suspend fun insertInterpret(interpret: Interpret) = interpretDao.insert(interpret)

    override suspend fun deleteInterpret(interpret: Interpret) = interpretDao.delete(interpret)

    override suspend fun updateInterpret(interpret: Interpret) = interpretDao.update(interpret)
}