package com.example.tada.screens.add

import androidx.lifecycle.ViewModel
import com.example.tada.extensions.onDefault
import com.example.tada.extensions.onIO
import com.example.tada.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _categoryName = MutableStateFlow("")
    val categoryName: StateFlow<String> = _categoryName

    fun addCategory(title: String) {
        onIO {
            taskRepository.saveCategory(title)
        }
    }

    fun onCategoryTitleChange(title: String) {
        onDefault {
            _categoryName.emit(title)
        }
    }
}