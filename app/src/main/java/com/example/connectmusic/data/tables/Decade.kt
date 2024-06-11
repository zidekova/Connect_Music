package com.example.connectmusic.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class reperezentujuca jeden riadok v databaze v tabulke [decade].
 */
@Entity(tableName = "decade")
data class Decade(
    @PrimaryKey(autoGenerate = true)
    val id_decade: Int = 0,
    val name_decade: String
)
