package com.example.tada.data.room

import androidx.room.Query
import com.example.tada.data.room.models.RoomTask

interface TaskDao : RoomBaseDao<RoomTask> {

    @Query("SELECT * FROM ")
    fun getAllTasksFromCategory(categoryId: Long): List<RoomTask>

}