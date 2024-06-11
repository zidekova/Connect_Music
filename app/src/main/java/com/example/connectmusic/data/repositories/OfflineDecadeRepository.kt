package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.DecadeDao
import kotlinx.coroutines.flow.Flow

class OfflineDecadeRepository(private val decadeDao: DecadeDao) : DecadesRepository {
    override fun getAllDecadesNames(): Flow<List<String>> = decadeDao.getAllDecadesNames()
}