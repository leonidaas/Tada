package de.leonfuessner.tada

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.leonfuessner.tada.screens.AddCategoryScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.pager.ExperimentalPagerApi
import de.leonfuessner.tada.screens.detail.DetailScreen
import de.leonfuessner.tada.screens.overview.OverviewScreen

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalMaterialNavigationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 24.dp)
    ) {
        NavHost(
            navController = navController,
            startDestination = TadaScreen.Overview.route
        ) {
            composable(
                route = TadaScreen.Overview.route
            ) {
                OverviewScreen(
                    onCategoryClick = { categoryId ->
                        navController.navigate(
                            TadaScreen.Detail.withArgs(categoryId)
                        )
                    },
                    onAddCategoryClick = {
                        navController.navigate(TadaScreen.AddCategory.route) {
                            popUpTo(TadaScreen.Overview.route)
                        }
                    }
                )
            }

            composable(
                route = TadaScreen.Detail.route + "{categoryId}",
                arguments = listOf(
                    navArgument("categoryId") {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("categoryId")
                if (id != null) {
                    DetailScreen(
                        viewModel = hiltViewModel(backStackEntry),
                        onNavigateUp = {
                            navController.navigate(TadaScreen.Overview.route) {
                                navController.popBackStack()
                            }
                        }
                    )
                }
            }

            bottomSheet(
                route = TadaScreen.AddCategory.route
            ) {
                AddCategoryScreen(
                    onCategoryAdd = {
                        navController.navigate(TadaScreen.Overview.route)
                    }
                )
            }
        }
    }

}