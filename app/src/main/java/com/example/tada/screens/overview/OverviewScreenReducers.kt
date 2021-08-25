package com.example.tada.screens.overview

fun createCategoryReducer(
    state: OverviewScreenState,
    action: OverviewScreenAction.CreateCategory
): OverviewScreenState {
    return state.copy(bottomSheetCollapsed = false)
}

fun categoryCreatedReducer(
    state: OverviewScreenState,
    action: OverviewScreenAction.CategoryCreated
): OverviewScreenState = state.copy(
    categories = state.categories.toMutableList().apply { add(action.category) }
)


fun categoryClickedReducer(
    state: OverviewScreenState,
    action: OverviewScreenAction.CategoryClicked
): OverviewScreenState {
    return state
}