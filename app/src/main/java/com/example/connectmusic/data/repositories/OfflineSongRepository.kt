package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.SongDao
import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.flow.Flow

class OfflineSongRepository(private val songDao: SongDao) : SongRepository {
    override fun getSongsByGenre(genre: String): List<Song> = songDao.getSongsByGenre(genre)
    override fun getSongsByInterpret(interpret: String): List<Song> = songDao.getSongsByInterpret(interpret)
    override fun getSongsByDecade(decade: String): List<Song> = songDao.getSongsByDecade(decade)
    override fun getSongById(id: Int): Song? = songDao.getSongById(id)
    override fun getSongName(id: Int): String = songDao.getSongName(id)
    override fun getInterpretBySongId(id: Int): String = songDao.getInterpretBySongId(id)
    override fun getGenreBySongId(id: Int): String? = songDao.getGenreBySongId(id)
    override fun getDecadeBySongId(id: Int): String? = songDao.getDecadeBySongId(id)
}