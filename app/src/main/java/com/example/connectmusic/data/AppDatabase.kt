package com.example.connectmusic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.connectmusic.data.daos.DecadeDao
import com.example.connectmusic.data.daos.GenreDao
import com.example.connectmusic.data.daos.InterpretDao
import com.example.connectmusic.data.daos.PlaylistDao
import com.example.connectmusic.data.daos.PlaylistSongDao
import com.example.connectmusic.data.daos.SongDao
import com.example.connectmusic.data.tables.Decade
import com.example.connectmusic.data.tables.Genre
import com.example.connectmusic.data.tables.Interpret
import com.example.connectmusic.data.tables.Playlist
import com.example.connectmusic.data.tables.PlaylistSong
import com.example.connectmusic.data.tables.Song

/**
 * Databazova trieda so singleton objektom.
 */
@Database(entities = [Genre::class, Decade::class, Interpret::class, Song::class, Playlist::class, PlaylistSong::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao
    abstract fun decadeDao(): DecadeDao
    abstract fun interpretDao(): InterpretDao
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun playlistSongDao(): PlaylistSongDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "CM_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}