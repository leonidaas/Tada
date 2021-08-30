package com.example.tada.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tada.extensions.onDefault
import com.example.tada.model.Category
import com.example.tada.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


//data class OverviewScreenState(
//    val categories: List<Category>,
//    val isLoading: Boolean
//) {
//    companion object {
//        fun initial() = OverviewScreenState(emptyList(), false)
//    }
//}

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

//    private val _state = MutableStateFlow(OverviewScreenState.initial())
//    val state: StateFlow<OverviewScreenState> = _state
//
//    private val pendingActions = MutableSharedFlow<OverviewScreenAction>()

    val categories = repository.getCategories().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
//        onDefault {
//            pendingActions.collect { action ->
//                val newState = when (action) {
//                    is OverviewScreenAction.CategoriesLoaded -> categoriesLoadedReducer(
//                        state.value,
//                        action
//                    )
//                    is OverviewScreenAction.CategoryCreated -> categoryCreatedReducer(
//                        state.value,
//                        action
//                    )
//                    is OverviewScreenAction.CategoryClicked -> categoryClickedReducer(
//                        state.value,
//                        action
//                    )
//
//                }
//
//                _state.emit(newState)
//            }
//
//            categories.collectLatest {
//                submitAction(
//                    OverviewScreenAction.CategoriesLoaded(it)
//                )
//            }
//        }
    }

//    fun submitAction(action: OverviewScreenAction) {
//        onDefault {
//            pendingActions.emit(action)
//        }
//    }
}