package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.GenreDao
import com.example.connectmusic.data.tables.Genre
import kotlinx.coroutines.flow.Flow

class OfflineGenreRepository(private val genreDao: GenreDao) : GenreRepository {
    override fun getAllGenresStream(): Flow<List<Genre>> = genreDao.getAllGenres()
    override fun getAllGenresNames(): Flow<List<String>> = genreDao.getAllGenresNames()

    override fun getGenreStream(id: Int): Flow<Genre?> = genreDao.getGenre(id)

    override suspend fun insertGenre(genre: Genre) = genreDao.insert(genre)

    override suspend fun deleteGenre(genre: Genre) = genreDao.delete(genre)

    override suspend fun updateGenre(genre: Genre) = genreDao.update(genre)
}