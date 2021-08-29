package com.example.tada.data.room

import androidx.room.*
import com.example.tada.data.result.RoomCategoryResult
import com.example.tada.data.room.models.RoomCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : RoomBaseDao<RoomCategory> {

    @Query("SELECT * FROM categories")
    suspend fun getAll(): Flow<List<RoomCategoryResult>>

    @Query("DELETE FROM categories WHERE id = :categoryId")
    fun deleteCategory(categoryId: Long)

}