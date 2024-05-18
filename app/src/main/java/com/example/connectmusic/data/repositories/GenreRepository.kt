package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getAllGenresStream(): Flow<List<Genre>>
    fun getGenreStream(id: Int): Flow<Genre?>
    suspend fun insertGenre(genre: Genre)
    suspend fun deleteGenre(genre: Genre)
    suspend fun updateGenre(genre: Genre)
}