package com.example.tada.data.result

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tada.data.room.models.RoomCategory
import com.example.tada.data.room.models.RoomTask

class RoomCategoryResult(
    @Embedded
    val category: RoomCategory,

    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId",
        entity = RoomTask::class
    )
    val tasks: List<RoomTask>
)