package com.example.tada.repository

import com.example.tada.data.room.CategoryDao
import com.example.tada.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {

    fun getCategories(): Flow<List<Category>> {
        return flowOf(listOf(Category(123, "Adw", listOf())))
    }


}