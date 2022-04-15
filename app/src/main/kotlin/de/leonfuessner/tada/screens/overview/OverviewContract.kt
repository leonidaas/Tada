package de.leonfuessner.tada.screens.overview

import de.leonfuessner.tada.core.ViewEvent
import de.leonfuessner.tada.core.ViewSideEffect
import de.leonfuessner.tada.core.ViewState
import de.leonfuessner.tada.model.Category

sealed class OverviewContract {

    data class State(
        val loading: Boolean,
        val categories: List<Category>
    ) : ViewState

    sealed class Event : ViewEvent {
        class CategoryClicked(val id: String) : Event()
        class DeleteCategoryClicked(val id: String) : Event()
        object AddCategoryClicked : Event()
    }

    sealed class SideEffect : ViewSideEffect {
        sealed class Navigation : SideEffect() {
            class ToDetail(val id: String) : Navigation()
            object ToAddCategory : Navigation()
        }
    }
}
