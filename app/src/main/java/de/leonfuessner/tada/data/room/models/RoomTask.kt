package de.leonfuessner.tada.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

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