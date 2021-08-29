package com.example.tada.data.clients

import com.example.tada.data.result.RoomCategoryResult
import com.example.tada.data.room.TadaDatabase
import com.example.tada.model.Category
import com.example.tada.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryDatabaseClient @Inject constructor(
    tadaDatabase: TadaDatabase
) {

    private val categoryDao = tadaDatabase.categoryDao()

    private val tasksDao = tadaDatabase.taskDao()

    suspend fun getAll(): Flow<List<Category>> {
        return categoryDao
            .getAll()
            .map { it.map(::roomToModel) }
    }

    suspend fun delete(category: Category) {
        withContext(Dispatchers.IO) {
             categoryDao.deleteCategory(category.id)
        }
    }
}

fun roomToModel(result: RoomCategoryResult): Category =
    Category(
        result.category.id,
        result.category.title,
        result.tasks.map {
            Task(it.id, it.task, it.completed)
        }
    )