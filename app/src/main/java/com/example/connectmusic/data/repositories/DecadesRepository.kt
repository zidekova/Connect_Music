package com.example.connectmusic.data.repositories

import kotlinx.coroutines.flow.Flow

interface DecadesRepository {
    fun getAllDecadesNames(): Flow<List<String>>
}