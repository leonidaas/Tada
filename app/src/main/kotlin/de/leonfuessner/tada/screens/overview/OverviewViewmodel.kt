package de.leonfuessner.tada.screens.overview

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leonfuessner.tada.core.MviViewModel
import de.leonfuessner.tada.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val repository: TaskRepository
) : MviViewModel<OverviewContract.Event, OverviewContract.State, OverviewContract.SideEffect>() {

    private val _dropdownIsShownForCategory = MutableStateFlow(Pair("", false))
//    val dropDownIsShownForCategory: StateFlow<Pair<String, Boolean>> = _dropdownIsShownForCategory

    init {
        viewModelScope.launch {
            observeCategories()
        }
    }

    fun showDropdown(id: String? = null, shouldShow: Boolean) {
        viewModelScope.launch {
            if (id != null) {
                _dropdownIsShownForCategory.emit(Pair(id, shouldShow))
            } else {
                _dropdownIsShownForCategory.emit(Pair("", shouldShow))
            }
        }
    }

    private suspend fun observeCategories() {
        repository
            .getCategories()
            .collect {
                setState { copy(loading = false, categories = it) }
            }
    }


    private fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            onIO {
//            showDropdown("", false)
                repository.deleteCategory(categoryId)
            }
        }
    }

    override fun setInitialState() = OverviewContract.State(false, emptyList())

    override fun handleEvent(event: OverviewContract.Event) {
        when (event) {
            OverviewContract.Event.AddCategoryClicked -> setEffect {
                OverviewContract.SideEffect.Navigation.ToAddCategory
            }
            is OverviewContract.Event.CategoryClicked -> setEffect {
                OverviewContract.SideEffect.Navigation.ToDetail(event.id)
            }
            is OverviewContract.Event.DeleteCategoryClicked -> {
                deleteCategory(event.id)
            }
        }
    }

}