package com.example.connectmusic.data.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entity data class reperezentujuca jeden riadok v databaze v tabulke [song].
 */
@Entity(tableName = "song", foreignKeys = [
    ForeignKey(entity = Interpret::class, parentColumns = ["id_interpret"], childColumns = ["id_interpret"], onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION),
    ForeignKey(entity = Decade::class, parentColumns = ["id_decade"], childColumns = ["id_decade"], onDelete = ForeignKey.NO_ACTION, onUpdate = ForeignKey.NO_ACTION)
])
data class Song(
    @PrimaryKey(autoGenerate = true)
    val id_song: Int = 0,
    val name_song: String,
    @ColumnInfo(name = "id_interpret", index = true)
    val id_interpret: Int,
    @ColumnInfo(name = "id_decade", index = true)
    val id_decade: Int
)
