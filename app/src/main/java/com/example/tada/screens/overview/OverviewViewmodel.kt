package com.example.tada.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tada.extensions.onDefault
import com.example.tada.extensions.onIO
import com.example.tada.model.Category
import com.example.tada.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _dropdownIsShownForCategory = MutableStateFlow(Pair("", false))
    val dropDownIsShownForCategory: StateFlow<Pair<String, Boolean>> = _dropdownIsShownForCategory

    fun showDropdown(id: String? = null, shouldShow: Boolean) {
        onDefault {
            if (id != null) {
                _dropdownIsShownForCategory.emit(Pair(id, shouldShow))
            } else {
                _dropdownIsShownForCategory.emit(Pair("", shouldShow))
            }
        }
    }

    val categories = repository
        .getCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun deleteCategory(categoryId: String) {
        onIO {
            showDropdown("", false)
            repository.deleteCategory(categoryId)
        }
    }

}