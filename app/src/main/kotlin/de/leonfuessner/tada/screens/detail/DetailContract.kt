package de.leonfuessner.tada.screens.detail

import de.leonfuessner.tada.core.ViewEvent
import de.leonfuessner.tada.core.ViewSideEffect
import de.leonfuessner.tada.core.ViewState
import de.leonfuessner.tada.model.Category
import de.leonfuessner.tada.model.Task

class DetailContract {

    //actual screen state
    data class State(
        val category: Category? = null,
        val isEditMode: Boolean = false,
        val shouldShowDropdown: Boolean = false
    ) : ViewState

    //onScreenEvent
    sealed class Event : ViewEvent {
        object NavigateUpClicked : Event()
        object MoreButtonClicked : Event()
        object DismissClicked : Event()
        object RemoveDoneTasksClicked : Event()
        object RemoveAllTasksClicked : Event()
        class OnCheckClicked(val task: Task, val checked: Boolean) : Event()
        object EditModeChanged : Event()
        class ItemAdded(val task: String) : Event()
    }

    //navigation
    sealed class SideEffect : ViewSideEffect {
        sealed class Navigation : SideEffect() {
            object ToOverview : Navigation()
        }
    }

}