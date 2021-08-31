package com.example.tada.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text


@Entity(
    tableName = "tasks"
)
data class RoomTask(
    @PrimaryKey
    val id: String,

    val categoryId: String,

    val task: String,

    val completed: Boolean
)