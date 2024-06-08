package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.DecadeDao
import com.example.connectmusic.data.tables.Decade
import kotlinx.coroutines.flow.Flow

class OfflineDecadeRepository(private val decadeDao: DecadeDao) : DecadesRepository {
    override fun getAllDecadesStream(): Flow<List<Decade>> = decadeDao.getAllDecades()
    override fun getAllDecadesNames(): Flow<List<String>> = decadeDao.getAllDecadesNames()

    override fun getDecadeStream(id: Int): Flow<Decade?> = decadeDao.getDecade(id)

    override suspend fun insertDecade(decade: Decade) = decadeDao.insert(decade)

    override suspend fun deleteDecade(decade: Decade) = decadeDao.delete(decade)

    override suspend fun updateDecade(decade: Decade) = decadeDao.update(decade)
}