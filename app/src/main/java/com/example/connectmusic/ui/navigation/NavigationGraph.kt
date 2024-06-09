package com.example.connectmusic.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
//import com.example.connectmusic.data.repositories.DecadeRepository
import com.example.connectmusic.data.repositories.GenreRepository
import com.example.connectmusic.data.repositories.InterpretRepository
import com.example.connectmusic.data.tables.Song
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.home.HomeDestination
import com.example.connectmusic.ui.home.HomeScreen
import com.example.connectmusic.ui.playlist.PlaylistDetailsDestination
import com.example.connectmusic.ui.playlist.PlaylistDetailsScreen
import com.example.connectmusic.ui.playlist.PlaylistEntryDestination
import com.example.connectmusic.ui.playlist.PlaylistEntryScreen
import com.example.connectmusic.ui.search.SearchDestination
import com.example.connectmusic.ui.search.SearchScreen
import com.example.connectmusic.ui.search.SearchViewModel
import com.example.connectmusic.ui.song.PlaylistSongDestination
import com.example.connectmusic.ui.song.PlaylistSongScreen
import com.example.connectmusic.ui.song.SongDestination
import com.example.connectmusic.ui.song.SongScreen

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
                navigateToSearchEntry = { navController.navigate(SearchDestination.route) },
                navigateToPlaylistDetails = { playlistId ->
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
        composable(route = SearchDestination.route) {
            SearchScreen(
                navigateBack = { navController.popBackStack() },
                navigateToSongDetail = { songId ->
                    navController.navigate("${SongDestination.route}/$songId")
                }
            )
        }
        composable(
            route = SongDestination.routeWithArgs,
            arguments = listOf(navArgument(SongDestination.songIdArg) {
                type = NavType.IntType
            })
        ) {backStackEntry ->
                // Retrieve the songId from the arguments
                val songId = backStackEntry.arguments?.getInt(SongDestination.songIdArg) ?: return@composable

                SongScreen(
                    songId = songId,
                    navigateBack = { navController.popBackStack() }
                )
        }
        composable(
            route = PlaylistDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(PlaylistDetailsDestination.playlistIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getInt(PlaylistDetailsDestination.playlistIdArg) ?: return@composable

            PlaylistDetailsScreen(
                navigateToSongDetails = { songId ->
                    navController.navigate("${PlaylistSongDestination.route}/$songId/$playlistId")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = PlaylistSongDestination.routeWithArgs,
            arguments = listOf(
                navArgument(PlaylistSongDestination.songIdArg) {
                    type = NavType.IntType
                },
                navArgument(PlaylistSongDestination.playlistIdArg) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getInt(PlaylistSongDestination.songIdArg) ?: return@composable
            val playlistId = backStackEntry.arguments?.getInt(PlaylistSongDestination.playlistIdArg) ?: return@composable
            Log.d("NavigationGraph", "Navigating to PlaylistSongScreen with songId: $songId and playlistId: $playlistId")

            PlaylistSongScreen(
                songId = songId,
                playlistId = playlistId,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}