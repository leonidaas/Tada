package com.example.tada.screens.add

import androidx.lifecycle.ViewModel
import com.example.tada.extensions.onIO
import com.example.tada.model.Category
import com.example.tada.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    fun addCategory(title: String) {
        onIO {
            taskRepository.saveCategory(title)
        }
    }
}