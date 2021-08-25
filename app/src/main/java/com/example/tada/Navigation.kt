package com.example.tada

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.tada.screens.detail.DetailScreen
import com.example.tada.screens.overview.OverviewScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TadaScreen.Overview.route
    ) {
        composable(route = TadaScreen.Overview.route) {
            OverviewScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(
                        TadaScreen.Detail.withArgs("$categoryId")
                    )
                }
            )
        }

        composable(
            route = TadaScreen.Detail.withArgs("categoryId"),
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.LongType
                    nullable = false
                }
            )
        ) {
            DetailScreen(navController = navController)
        }
    }
}