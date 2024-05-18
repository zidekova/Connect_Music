package com.example.connectmusic.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decade")
data class Decade(
    @PrimaryKey(autoGenerate = true)
    val idDecade: Int = 0,
    val nameDecade: String
)
