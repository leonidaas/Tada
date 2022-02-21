package de.leonfuessner.tada.data.clients

import de.leonfuessner.tada.data.result.RoomCategoryResult
import de.leonfuessner.tada.data.room.TadaDatabase
import de.leonfuessner.tada.data.room.models.RoomCategory
import de.leonfuessner.tada.data.room.models.RoomTask
import de.leonfuessner.tada.model.Category
import de.leonfuessner.tada.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    suspend fun updateTask(task: Task) {
        withContext(Dispatchers.IO) {
            tasksDao.update(modelToRoom(task))
        }
    }

    suspend fun deleteCompletedTasks(categoryId: String) {
        withContext(Dispatchers.IO) {
            tasksDao.deleteCompletedTasks(categoryId)
        }
    }

    suspend fun deleteAllTasks(categoryId: String) {
        withContext(Dispatchers.IO) {
            tasksDao.deleteAllTasks(categoryId)
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