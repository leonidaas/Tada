package com.example.tada.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tada.di.AppModule_ProvideTaskRepositoryFactory
import com.example.tada.extensions.onDefault
import com.example.tada.extensions.onIO
import com.example.tada.model.Task
import com.example.tada.repository.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    val categoryId = savedStateHandle["categoryId"] ?: ""

    val tasks = taskRepository
        .getCategory(categoryId)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun onCheckChange(task: Task, checked: Boolean) {
        onIO {
            taskRepository.updateTask(task.copy(isDone = checked))
        }
    }

}