package com.ryanthahir.astroapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savedThings")
data class Saved(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "author") val author: String?,
        @ColumnInfo(name = "saved") var saved: String?
)
