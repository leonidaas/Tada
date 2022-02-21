package de.leonfuessner.tada.data.result

import androidx.room.Embedded
import androidx.room.Relation
import de.leonfuessner.tada.data.room.models.RoomCategory
import de.leonfuessner.tada.data.room.models.RoomTask

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