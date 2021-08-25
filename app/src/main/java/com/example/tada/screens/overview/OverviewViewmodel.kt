package com.example.tada.screens.overview

import androidx.lifecycle.ViewModel
import com.example.tada.TaskRepository
import com.example.tada.extensions.onDefault
import com.example.tada.model.Category
import com.example.tada.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

sealed class OverviewScreenAction {
    object CreateCategory : OverviewScreenAction()
    class CategoryCreated(val category: Category) : OverviewScreenAction()
    class CategoryClicked(val id: Long) : OverviewScreenAction()
}

data class OverviewScreenState(
    val categories: List<Category> = emptyList(),
    val bottomSheetCollapsed: Boolean = true
)

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    repository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OverviewScreenState())
    val state: StateFlow<OverviewScreenState>
        get() = _state

    private val pendingActions = MutableSharedFlow<OverviewScreenAction>()

//    val tasks = listOf(
//        Task(1, "Aufräumen", false),
//        Task(1, "Aufräumen", false),
//        Task(1, "Aufräumen", false),
//        Task(1, "Aufräumen", false)
//    )
//
//    private val categories = listOf(
//        Category(1, "Personal", tasks),
//        Category(2, "Work", tasks),
//        Category(3, "Kitchen", tasks),
//    )

    init {
        onDefault {
            pendingActions.collect { action ->
                val newState = when (action) {
                    is OverviewScreenAction.CreateCategory -> createCategoryReducer(
                        state.value,
                        action
                    )
                    is OverviewScreenAction.CategoryCreated -> categoryCreatedReducer(
                        state.value,
                        action
                    )
                    is OverviewScreenAction.CategoryClicked -> categoryClickedReducer(
                        state.value,
                        action
                    )

                }

                _state.emit(newState)
            }
        }
    }

    fun submitAction(action: OverviewScreenAction) {
        onDefault {
            pendingActions.emit(action)
        }
    }
}