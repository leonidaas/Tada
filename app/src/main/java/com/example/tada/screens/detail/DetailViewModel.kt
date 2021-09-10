package com.example.tada.screens.detail

import androidx.compose.material.contentColorFor
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tada.di.AppModule_ProvideTaskRepositoryFactory
import com.example.tada.extensions.onDefault
import com.example.tada.extensions.onIO
import com.example.tada.model.Category
import com.example.tada.model.Task
import com.example.tada.repository.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    val categoryId = savedStateHandle["categoryId"] ?: ""

    private val _isInAddMode = MutableStateFlow(false)
    val isInAddMode: StateFlow<Boolean> get() = _isInAddMode

    private val _hideDoneTasks = MutableStateFlow(false)

    private val _taskText = MutableStateFlow("")
    val taskText: StateFlow<String> get() = _taskText

    private val _dropdownIsShown = MutableStateFlow(false)
    val dropdownIsShown: StateFlow<Boolean> get() = _dropdownIsShown

    val categoryFlow = _hideDoneTasks.flatMapLatest { hideCompleted ->
        taskRepository
            .getCategory(categoryId)
            .map { category ->
                val newCategory = if (hideCompleted) {
                    category.copy(tasks = category.tasks.filter { !it.isDone })
                } else {
                    category
                }
                newCategory
            }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)


    fun showDropdown(shouldShow: Boolean) {
        onDefault {
            _dropdownIsShown.emit(shouldShow)
        }
    }

    fun switchAddMode(on: Boolean) {
        onDefault {
            _isInAddMode.emit(on)
        }
    }

    fun onTextChange(text: String) {
        onDefault {
            _taskText.emit(text)
        }
    }

    fun addTask() {
        onDefault {
            taskRepository.addTask(
                Task(
                    UUID.randomUUID().toString(),
                    categoryId,
                    taskText.value.trim(),
                    false
                )
            )
            _isInAddMode.emit(false)
            _taskText.emit("")
        }
    }

    fun onCheckChange(task: Task, checked: Boolean) {
        onIO {
            taskRepository.updateTask(task.copy(isDone = checked))
        }
    }

    fun onRemoveDoneTasks(hide: Boolean) {
        onDefault {
            _dropdownIsShown.emit(false)
            _hideDoneTasks.emit(hide)
        }
    }

}