package com.example.tada.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tada.data.room.models.RoomTask

@Dao
interface TaskDao : RoomBaseDao<RoomTask> {

    @Query("SELECT * FROM tasks WHERE categoryId = :categoryId AND completed != :hideCompleted")
    fun getAllTasksFromCategory(categoryId: Long, hideCompleted: Boolean): List<RoomTask>

}