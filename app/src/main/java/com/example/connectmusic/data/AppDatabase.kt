package com.example.connectmusic.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.connectmusic.data.daos.DecadeDao
import com.example.connectmusic.data.daos.GenreDao
import com.example.connectmusic.data.daos.InterpretDao
import com.example.connectmusic.data.daos.PlaylistDao
import com.example.connectmusic.data.daos.SongDao
import com.example.connectmusic.data.tables.Decade
import com.example.connectmusic.data.tables.Genre
import com.example.connectmusic.data.tables.Interpret
import com.example.connectmusic.data.tables.Playlist
import com.example.connectmusic.data.tables.Song

@Database(entities = [Genre::class, Decade::class, Interpret::class, Song::class, Playlist::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao
    abstract fun decadeDao(): DecadeDao
    abstract fun interpretDao(): InterpretDao
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}