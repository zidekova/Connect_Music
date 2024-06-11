package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.tables.Song

interface SongRepository {
    fun getSongsByGenre(genre: String): List<Song>
    fun getSongsByInterpret(interpret: String): List<Song>
    fun getSongsByDecade(decade: String): List<Song>
    fun getSongById(id: Int): Song?
    fun getSongName(id: Int): String
    fun getInterpretBySongId(id: Int): String
    fun getGenreBySongId(id: Int): String?
    fun getDecadeBySongId(id: Int): String?
}