package com.example.connectmusic.data.tables

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "interpret", foreignKeys = [
    ForeignKey(entity = Genre::class, parentColumns = ["idGenre"], childColumns = ["idGenre"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Interpret(
    @PrimaryKey(autoGenerate = true)
    val idInterpret: Int = 0,
    val nameInterpret: String,
    val idGenre: Int
)
