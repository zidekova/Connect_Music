package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    fun getAllSongsStream(): Flow<List<Song>>
    fun getSongStream(id: Int): Flow<Song?>
    suspend fun insertSong(song: Song)
    suspend fun deleteSong(song: Song)
    suspend fun updateSong(song: Song)
}