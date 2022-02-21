package de.leonfuessner.tada.screens.add

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leonfuessner.tada.core.MviViewModel
import de.leonfuessner.tada.repository.TaskRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : MviViewModel<AddCategoryContract.Event, AddCategoryContract.State, AddCategoryContract.SideEffect>() {

    private fun addCategory() {
        viewModelScope.launch {
            onIO {
                taskRepository.saveCategory(viewState.value.imagePosition, viewState.value.text)
                onMain {
                    setEffect {
                        AddCategoryContract.SideEffect.Navigation.ToOverview
                    }
                }
            }
        }
    }

    override fun setInitialState(): AddCategoryContract.State = AddCategoryContract.State()

    override fun handleEvent(event: AddCategoryContract.Event) {
        when (event) {
            is AddCategoryContract.Event.ImageChanged -> setState { copy(imagePosition = event.position) }
            is AddCategoryContract.Event.TextChanged -> setState { copy(text = event.text) }
            AddCategoryContract.Event.CategoryAdded -> addCategory()
        }
    }
}