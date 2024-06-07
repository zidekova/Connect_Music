package com.example.connectmusic.data.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "interpret", foreignKeys = [
    ForeignKey(entity = Genre::class, parentColumns = ["id_genre"], childColumns = ["id_genre"], onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION)
])
data class Interpret(
    @PrimaryKey(autoGenerate = true)
    val id_interpret: Int = 0,
    val name_interpret: String,
    @ColumnInfo(name = "id_genre", index = true)
    val id_genre: Int
)
