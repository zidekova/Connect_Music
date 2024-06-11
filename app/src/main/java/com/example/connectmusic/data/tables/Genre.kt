package com.example.connectmusic.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class reperezentujuca jeden riadok v databaze v tabulke [genre].
 */
@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val id_genre: Int = 0,
    val name_genre: String
)
