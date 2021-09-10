package com.example.tada.data.clients

import com.example.tada.data.result.RoomCategoryResult
import com.example.tada.data.room.TadaDatabase
import com.example.tada.data.room.models.RoomCategory
import com.example.tada.data.room.models.RoomTask
import com.example.tada.model.Category
import com.example.tada.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class CategoryDatabaseClient @Inject constructor(
    tadaDatabase: TadaDatabase
) {

    private val categoryDao = tadaDatabase.categoryDao()

    private val tasksDao = tadaDatabase.taskDao()

    fun getAll(): Flow<List<Category>> {
        return categoryDao
            .getAll()
            .mapNotNull { it.map(::roomToModel) }
    }

    fun get(categoryId: String): Flow<Category> {
        return categoryDao
            .get(categoryId)
            .mapNotNull { roomToModel(it) }
    }

    suspend fun delete(categoryId: String) {
        withContext(Dispatchers.IO) {
            categoryDao.deleteCategory(categoryId)
        }
    }

    suspend fun save(imageId: Int, title: String) {
        val id = UUID.randomUUID().toString()
        withContext(Dispatchers.IO) {
            categoryDao.insert(
                RoomCategory(id = id, imageId = imageId, title = title)
            )
        }
    }

    suspend fun addTask(task: Task) {
        withContext(Dispatchers.IO) {
            tasksDao.insert(modelToRoom(task))
        }
    }

    suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            tasksDao.update(modelToRoom(task))
        }
    }
}

fun roomToModel(result: RoomCategoryResult): Category =
    Category(
        result.category.id,
        result.category.imageId,
        result.category.title,
        result.tasks
            .map {
                Task(it.id, result.category.id, it.task, it.completed)
            }
    )

fun modelToRoom(task: Task): RoomTask =
    RoomTask(
        task.id,
        task.categoryId,
        task.text,
        task.isDone
    )