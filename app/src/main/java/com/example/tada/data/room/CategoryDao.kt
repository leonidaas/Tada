package com.example.tada.data.room

import androidx.room.*
import com.example.tada.data.result.RoomCategoryResult
import com.example.tada.data.room.models.RoomCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : RoomBaseDao<RoomCategory> {

    @Transaction
    @Query("SELECT * FROM categories")
    fun getAll(): Flow<List<RoomCategoryResult>>

    @Transaction
    @Query("SELECT * FROM categories where id = :categoryId")
    fun get(categoryId: String): Flow<RoomCategoryResult>

    @Query("DELETE FROM categories WHERE id = :categoryId")
    fun deleteCategory(categoryId: String)

}