package de.leonfuessner.tada.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leonfuessner.tada.core.MviViewModel
import de.leonfuessner.tada.model.Task
import de.leonfuessner.tada.repository.TaskRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle,
) : MviViewModel<DetailContract.Event, DetailContract.State, DetailContract.SideEffect>() {


    val categoryId: String = savedStateHandle["categoryId"] ?: throw IllegalArgumentException()

    init {
        viewModelScope.launch {
            observeTasks()
        }
    }

    private suspend fun observeTasks() {
        taskRepository
            .getCategory(categoryId)
            .collect {
                setState { copy(category = it) }
            }
    }

    private fun addTask(text: String) {
        viewModelScope.launch {
            onIO {
                taskRepository.addTask(
                    Task(
                        UUID.randomUUID().toString(),
                        categoryId,
                        text.trim(),
                        false
                    )
                )
            }
        }
    }

    private fun onCheckChange(task: Task, checked: Boolean) {
        viewModelScope.launch {
            onIO {
                taskRepository.updateTask(task.copy(isDone = checked))
            }
        }
    }

    private fun removeFinishedTasks() {
        viewModelScope.launch {
            onIO {
                taskRepository.deleteCompletedTasks(categoryId)
            }

            onMain {
                setState {
                    copy(shouldShowDropdown = false)
                }
            }
        }

    }

    private fun removeAllTasks() {
        viewModelScope.launch {
            onIO {
                taskRepository.deleteAllTasks(categoryId)
            }
            onMain {
                setState {
                    copy(shouldShowDropdown = false)
                }
            }
        }
    }

    override fun setInitialState(): DetailContract.State = DetailContract.State()

    override fun handleEvent(event: DetailContract.Event) {
        when (event) {
            DetailContract.Event.NavigateUpClicked -> setEffect { DetailContract.SideEffect.Navigation.ToOverview }
            DetailContract.Event.DismissClicked -> setState {
                copy(
                    shouldShowDropdown = false
                )
            }
            DetailContract.Event.EditModeChanged -> setState {
                copy(isEditMode = !this.isEditMode)
            }
            is DetailContract.Event.ItemAdded -> {
                addTask(event.task)
                setState { copy(isEditMode = !this.isEditMode) }
            }
            DetailContract.Event.MoreButtonClicked -> setState {
                copy(
                    shouldShowDropdown = true
                )
            }
            is DetailContract.Event.OnCheckClicked -> {
                onCheckChange(event.task, event.checked)
            }
            DetailContract.Event.RemoveAllTasksClicked -> removeAllTasks()
            DetailContract.Event.RemoveDoneTasksClicked -> removeFinishedTasks()
        }
    }

}