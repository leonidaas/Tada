package com.example.tada.repository

import com.example.tada.data.clients.CategoryDatabaseClient
import com.example.tada.data.result.RoomCategoryResult
import com.example.tada.model.Category
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TaskRepository @Inject constructor(
    private val categoryClient: CategoryDatabaseClient
) {

    fun getCategories(): Flow<List<Category>> {
        return categoryClient.getAll()
    }

    fun getCategory(categoryId: String): Flow<Category> {
        return categoryClient.get(categoryId)
    }

    suspend fun saveCategory(title: String) {
        categoryClient.save(title)
    }

}