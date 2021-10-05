package de.leonfuessner.tada.repository

import de.leonfuessner.tada.data.clients.CategoryDatabaseClient
import de.leonfuessner.tada.model.Category
import de.leonfuessner.tada.model.Task
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

    suspend fun deleteCategory(categoryId: String) {
        categoryClient.delete(categoryId)
    }

    suspend fun addTask(task: Task) {
        categoryClient.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        return categoryClient.updateTask(task)
    }

    suspend fun deleteCompletedTasks(categoryId: String) {
        categoryClient.deleteCompletedTasks(categoryId)
    }

    suspend fun saveCategory(imageId: Int, title: String) {
        categoryClient.save(imageId, title)
    }

    suspend fun deleteAllTasks(categoryId: String) {
        categoryClient.deleteAllTasks(categoryId)
    }

}