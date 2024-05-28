package com.example.connectmusic.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.connectmusic.ui.home.HomeDestination
import com.example.connectmusic.ui.home.HomeScreen
import com.example.connectmusic.ui.playlist.PlaylistEntryDestination
import com.example.connectmusic.ui.playlist.PlaylistEntryScreen
import com.example.connectmusic.ui.search.SearchEntryDestination
import com.example.connectmusic.ui.search.SearchEntryScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun ConnectMusicNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToPlaylistEntry = { navController.navigate(PlaylistEntryDestination.route) },
                navigateToSearchEntry = { navController.navigate(SearchEntryDestination.route) }
//                navigateToItemUpdate = {
//                    navController.navigate("${ItemDetailsDestination.route}/${it}")
//                }
            )
        }
        composable(route = PlaylistEntryDestination.route) {
            PlaylistEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navigateToSearchEntry = { navController.navigate(SearchEntryDestination.route) }
            )
        }
        composable(route = SearchEntryDestination.route) {
            SearchEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
//        composable(
//            route = PlaylistDetailsDestination.routeWithArgs,
//            arguments = listOf(navArgument(PlaylistDetailsDestination.itemIdArg) {
//                type = NavType.IntType
//            })
//        ) {
//            PlaylistDetailsScreen(
//                navigateToEditItem = { navController.navigate("${PlaylistEditDestination.route}/$it") },
//                navigateBack = { navController.navigateUp() }
//            )
//        }
//        composable(
//            route = EditDestination.routeWithArgs,
//            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
//                type = NavType.IntType
//            })
//        ) {
//            ItemEditScreen(
//                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
//            )
//        }
    }
}