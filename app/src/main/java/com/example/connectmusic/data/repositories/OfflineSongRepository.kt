package com.example.connectmusic.data.repositories

import com.example.connectmusic.data.daos.SongDao
import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.flow.Flow

class OfflineSongRepository(private val songDao: SongDao) : SongRepository {
    override fun getAllSongsStream(): Flow<List<Song>> = songDao.getAllSongs()

    override fun getSongStream(id: Int): Flow<Song?> = songDao.getSong(id)

    override suspend fun insertSong(song: Song) = songDao.insert(song)

    override suspend fun deleteSong(song: Song) = songDao.delete(song)

    override suspend fun updateSong(song: Song) = songDao.update(song)
}