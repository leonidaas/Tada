package com.example.tada.screens.overview


fun categoriesLoadedReducer(
    state: OverviewScreenState,
    action: OverviewScreenAction.CategoriesLoaded
): OverviewScreenState {
    return state.copy(categories = action.categories)
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