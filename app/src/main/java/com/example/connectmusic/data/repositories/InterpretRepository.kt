package com.example.connectmusic.data.repositories

import kotlinx.coroutines.flow.Flow

interface InterpretRepository {
    fun getAllInterpretsNames(): Flow<List<String>>
}