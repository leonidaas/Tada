package com.example.tada.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "categories"
)
data class RoomCategory(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "imageId")
    val imageId: Int,

    @ColumnInfo(name = "title")
    val title: String
)