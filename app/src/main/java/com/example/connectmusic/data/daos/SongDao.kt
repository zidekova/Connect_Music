package com.example.connectmusic.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.connectmusic.data.tables.Song

@Dao
interface SongDao {
    @Query("SELECT * FROM song JOIN interpret USING(id_interpret) JOIN genre USING(id_genre) WHERE name_genre = :genre")
    fun getSongsByGenre(genre: String): List<Song>
    @Query("SELECT * FROM song JOIN interpret USING(id_interpret) WHERE name_interpret = :interpret")
    fun getSongsByInterpret(interpret: String): List<Song>
    @Query("SELECT * FROM song JOIN decade USING(id_decade) WHERE name_decade = :decade")
    fun getSongsByDecade(decade: String): List<Song>
    @Query("SELECT * FROM song WHERE id_song = :id")
    fun getSongById(id: Int): Song?
    @Query("SELECT name_song FROM song WHERE id_song = :id")
    fun getSongName(id: Int): String
    @Query("SELECT name_interpret FROM interpret JOIN song USING(id_interpret) WHERE id_song = :id")
    fun getInterpretBySongId(id: Int): String
    @Query("SELECT name_genre FROM genre JOIN interpret USING(id_genre) JOIN song USING(id_interpret) WHERE id_song = :id")
    fun getGenreBySongId(id: Int): String?
    @Query("SELECT name_decade FROM decade JOIN song USING(id_decade) WHERE id_song = :id")
    fun getDecadeBySongId(id: Int): String?
}