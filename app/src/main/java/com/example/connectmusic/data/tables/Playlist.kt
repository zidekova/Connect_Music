package com.example.connectmusic.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class reperezentujuca jeden riadok v databaze v tabulke [playlist].
 */
@Entity(tableName = "playlist")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val id_playlist: Int = 0,
    val name_playlist: String = "",
)
