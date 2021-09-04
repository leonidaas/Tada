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

    private val _dropdownIsShown = MutableStateFlow(false)
    val dropDownIsShown: StateFlow<Boolean> = _dropdownIsShown

    fun showDropdown(shouldShow: Boolean) {
        onDefault {
            _dropdownIsShown.emit(shouldShow)
        }
    }

    val categories = repository
        .getCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun deleteCategory(categoryId: String) {
        onIO {
            showDropdown(false)
            repository.deleteCategory(categoryId)
        }
    }

}