package fr.esaip.tvshowapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fr.esaip.tvshowapp.view.TVShowDetailsScreen
import fr.esaip.tvshowapp.view.TVShowScreen

sealed class Screen(val route: String) {
    object TVShowList : Screen("tv_show_list")
    object TVShowDetails : Screen("tv_show_details/{tvShowId}") {
        fun createRoute(tvShowId: Int) = "tv_show_details/$tvShowId"
    }
}

@Composable
fun TVShowNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.TVShowList.route
    ) {
        composable(Screen.TVShowList.route) {
            TVShowScreen(
                onTVShowClick = { tvShowId ->
                    navController.navigate(Screen.TVShowDetails.createRoute(tvShowId))
                }
            )
        }

        composable(
            route = Screen.TVShowDetails.route,
            arguments = listOf(
                navArgument("tvShowId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val tvShowId = backStackEntry.arguments?.getInt("tvShowId") ?: 0
            TVShowDetailsScreen(
                tvShowId = tvShowId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
