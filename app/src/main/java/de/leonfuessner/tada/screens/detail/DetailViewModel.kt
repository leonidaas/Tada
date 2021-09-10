package de.leonfuessner.tada.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leonfuessner.tada.extensions.onDefault
import de.leonfuessner.tada.extensions.onIO
import de.leonfuessner.tada.model.Task
import de.leonfuessner.tada.repository.TaskRepository
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