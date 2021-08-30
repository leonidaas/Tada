package com.example.tada


sealed class TadaScreen(val route: String) {
    object Overview : TadaScreen("overview_screen")
    object Detail : TadaScreen("detail_screen")
    object AddCategory : TadaScreen("add_category_screen")

    fun withArgs(vararg args: String) = buildString {
        append(route)
        args.forEach { arg ->
            append(arg)
        }
    }
}