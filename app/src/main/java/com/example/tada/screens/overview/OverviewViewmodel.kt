package com.example.tada.screens.overview

import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import com.example.tada.TaskRepository
import com.example.tada.extensions.onDefault
import com.example.tada.extensions.onIO
import com.example.tada.model.Category
import com.example.tada.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.lang.reflect.Constructor
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    repository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OverviewScreenState())
    val state: StateFlow<OverviewScreenState>
        get() = _state

    val tasks = listOf(
        Task(1, "Aufräumen", false),
        Task(1, "Aufräumen", false),
        Task(1, "Aufräumen", false),
        Task(1, "Aufräumen", false)
    )

    private val categories = MutableStateFlow<List<Category>>(
        listOf(
            Category(1, "Personal", tasks),
            Category(2, "Work", tasks),
            Category(3, "Kitchen", tasks),
        )
    )

    init {
        onDefault {
            categories.mapLatest {
                OverviewScreenState(
                    categories = it
                )
            }.collect {
                _state.value = it
            }
        }
    }

    fun addCategory(category: Category) {
        onIO {
            val newCategories = categories.value
            newCategories.toMutableList().add(category)
            categories.emit(newCategories)
        }
    }

}

data class OverviewScreenState(
    val categories: List<Category> = emptyList()
)