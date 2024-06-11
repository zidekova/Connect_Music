package com.example.connectmusic.data.repositories

import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getAllGenresNames(): Flow<List<String>>
}