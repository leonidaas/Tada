package de.leonfuessner.tada.screens.add

import de.leonfuessner.tada.core.ViewEvent
import de.leonfuessner.tada.core.ViewSideEffect
import de.leonfuessner.tada.core.ViewState
import de.leonfuessner.tada.ui.theme.icons

class AddCategoryContract {

    data class State(
        val imagePosition: Int = 0,
        val text: String = "",
    ) : ViewState

    //onScreenEvent
    sealed class Event : ViewEvent {
        class ImageChanged(val position: Int) : Event()
        class TextChanged(val text: String) : Event()
        object CategoryAdded : Event()
    }

    //navigation
    sealed class SideEffect : ViewSideEffect {
        sealed class Navigation : SideEffect() {
            object ToOverview : Navigation()
        }
    }

}