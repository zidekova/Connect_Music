package com.example.connectmusic.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val idGenre: Int = 0,
    val nameGenre: String
)
