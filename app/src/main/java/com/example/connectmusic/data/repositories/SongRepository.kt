package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    fun getAllSongsStream(): Flow<List<Song>>
    fun getSongStream(id: Int): Flow<Song?>
    fun getSongsByGenre(genre: String): List<Song>
    fun getSongsByInterpret(interpret: String): List<Song>
    fun getSongsByDecade(decade: String): List<Song>
    fun getSongById(id: Int): Song?
    fun getSongName(id: Int): String
    fun getInterpretBySongId(id: Int): String
    fun getGenreBySongId(id: Int): String?
    fun getDecadeBySongId(id: Int): String?
    suspend fun insertSong(song: Song)
    suspend fun deleteSong(song: Song)
    suspend fun updateSong(song: Song)
}