package de.leonfuessner.tada.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leonfuessner.tada.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _dropdownIsShownForCategory = MutableStateFlow(Pair("", false))
    val dropDownIsShownForCategory: StateFlow<Pair<String, Boolean>> = _dropdownIsShownForCategory

    fun showDropdown(id: String? = null, shouldShow: Boolean) {
        viewModelScope.launch {
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
        viewModelScope.launch(Dispatchers.IO) {
//            onIO {
            showDropdown("", false)
            repository.deleteCategory(categoryId)
//            }
        }

    }

}