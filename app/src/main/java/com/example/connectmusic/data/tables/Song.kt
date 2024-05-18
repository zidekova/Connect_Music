package com.example.connectmusic.data.tables

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "song", foreignKeys = [
    ForeignKey(entity = Interpret::class, parentColumns = ["idInterpret"], childColumns = ["idInterpret"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
    ForeignKey(entity = Decade::class, parentColumns = ["idDecade"], childColumns = ["idDecade"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
data class Song(
    @PrimaryKey(autoGenerate = true)
    val idSong: Int = 0,
    val nameSong: String,
    val idInterpret: Int,
    val idDecade: Int
)
