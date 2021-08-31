package com.example.tada.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tada.di.AppModule_ProvideTaskRepositoryFactory
import com.example.tada.extensions.onDefault
import com.example.tada.extensions.onIO
import com.example.tada.model.Task
import com.example.tada.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val taskRepository: TaskRepository
) : ViewModel() {


    val categoryId = MutableStateFlow<String>("")

    val tasks = categoryId.flatMapLatest {
        taskRepository
            .getCategory(it)

    }.stateIn(viewModelScope, SharingStarted.Lazily, null)


    fun initalize(id: String) {
        onDefault {
            categoryId.emit(id)
        }
    }

    fun onCheckChange(task: Task, checked: Boolean) {
        onIO {
            taskRepository.updateTask(task.copy(isDone = checked))
        }
    }

}