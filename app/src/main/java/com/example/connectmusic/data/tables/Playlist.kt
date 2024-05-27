package com.example.connectmusic.data.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "playlist", foreignKeys = [
    ForeignKey(entity = Song::class, parentColumns = ["idSong"], childColumns = ["idSong"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val idPlaylist: Int = 0,
    val namePlaylist: String,
    @ColumnInfo(name = "idSong", index = true)
    val idSong: Int
)
