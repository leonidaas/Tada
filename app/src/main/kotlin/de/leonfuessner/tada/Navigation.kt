package de.leonfuessner.tada

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.pager.ExperimentalPagerApi
import de.leonfuessner.tada.screens.add.AddCategoryContract
import de.leonfuessner.tada.screens.add.AddCategoryScreen
import de.leonfuessner.tada.screens.detail.DetailContract
import de.leonfuessner.tada.screens.detail.DetailScreen
import de.leonfuessner.tada.screens.overview.OverviewContract
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
            overview(route = TadaScreen.Overview.route, navController = navController)

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
                        onNavigationRequested = {
                            when (it) {
                                DetailContract.SideEffect.Navigation.ToOverview -> {
                                    navController.navigate(TadaScreen.Overview.route) {
                                        navController.popBackStack()
                                    }
                                }
                            }

                        }
                    )
                }
            }

            bottomSheet(
                route = TadaScreen.AddCategory.route
            ) {
                AddCategoryScreen(
                    onNavigationRequested = {
                        when (it) {
                            AddCategoryContract.SideEffect.Navigation.ToOverview -> {
                                navController.navigate(TadaScreen.Overview.route)
                            }
                        }
                    }
                )
            }
        }
    }
}

private fun NavGraphBuilder.overview(
    route: String,
    navController: NavController
) {
    composable(
        route = route
    ) {
        OverviewScreen(
            viewModel = hiltViewModel(),
            onNavigationRequested = { target ->
                when (target) {
                    is OverviewContract.SideEffect.Navigation.ToDetail -> {
                        navController.navigate(
                            TadaScreen.Detail.withArgs(target.id)
                        )
                    }
                    OverviewContract.SideEffect.Navigation.ToAddCategory -> {
                        navController.navigate(TadaScreen.AddCategory.route) {
                            popUpTo(TadaScreen.Overview.route)
                        }
                    }
                }
            }
        )
    }
}