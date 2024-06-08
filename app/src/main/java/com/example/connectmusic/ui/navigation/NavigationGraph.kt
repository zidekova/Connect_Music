package com.example.connectmusic.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
//import com.example.connectmusic.data.repositories.DecadeRepository
import com.example.connectmusic.data.repositories.GenreRepository
import com.example.connectmusic.data.repositories.InterpretRepository
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.home.HomeDestination
import com.example.connectmusic.ui.home.HomeScreen
import com.example.connectmusic.ui.playlist.PlaylistDetailsDestination
import com.example.connectmusic.ui.playlist.PlaylistDetailsScreen
import com.example.connectmusic.ui.playlist.PlaylistEntryDestination
import com.example.connectmusic.ui.playlist.PlaylistEntryScreen
import com.example.connectmusic.ui.search.SearchEntryDestination
import com.example.connectmusic.ui.search.SearchEntryScreen
import com.example.connectmusic.ui.search.SearchPickDestination
import com.example.connectmusic.ui.search.SearchPickScreen
import com.example.connectmusic.ui.search.SearchViewModel

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun ConnectMusicNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier,
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToPlaylistEntry = { navController.navigate(PlaylistEntryDestination.route) },
                navigateToSearchEntry = { navController.navigate(SearchEntryDestination.route) },
                navigateToPlaylistDetails = { playlistId ->
                    Log.d("Navigation", "Navigating to PlaylistDetails with ID: $playlistId")
                    navController.navigate("${PlaylistDetailsDestination.route}/$playlistId")
                }
            )
        }
        composable(route = PlaylistEntryDestination.route) {
            PlaylistEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = SearchEntryDestination.route) {
            SearchEntryScreen(
                onNext = { selectedMethod ->
                    navController.navigate(SearchPickDestination.route)
                }
            )
        }
        composable(route = SearchPickDestination.route) {
            SearchPickScreen(
                navigateBack = { navController.popBackStack() },
                onMethodSelected = {}
            )
        }

        composable(
            route = PlaylistDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(PlaylistDetailsDestination.playlistIdArg) {
                type = NavType.IntType
            })
        ) {
            PlaylistDetailsScreen(
                //navigateToEditItem = { navController.navigate("${PlaylistEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
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