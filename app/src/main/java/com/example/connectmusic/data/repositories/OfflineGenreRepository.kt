package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.GenreDao
import kotlinx.coroutines.flow.Flow

class OfflineGenreRepository(private val genreDao: GenreDao) : GenreRepository {
    override fun getAllGenresNames(): Flow<List<String>> = genreDao.getAllGenresNames()
}