package com.example.connectmusic.data.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_song", foreignKeys = [
    ForeignKey(entity = Playlist::class, parentColumns = ["id_playlist"], childColumns = ["id_playlist"], onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION),
    ForeignKey(entity = Song::class, parentColumns = ["id_song"], childColumns = ["id_song"], onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION)
])
data class PlaylistSong(
    @PrimaryKey(autoGenerate = true)
    val id_playlist_song: Int = 0,
    @ColumnInfo(name = "id_playlist", index = true)
    val id_playlist: Int,
    @ColumnInfo(name = "id_song", index = true)
    val id_song: Int
)