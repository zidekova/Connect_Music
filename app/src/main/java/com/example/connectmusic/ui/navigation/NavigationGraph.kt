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
import com.example.connectmusic.ui.playlist.PlaylistDetailsDestination
import com.example.connectmusic.ui.playlist.PlaylistDetailsScreen
import com.example.connectmusic.ui.playlist.NewPlaylistDestination
import com.example.connectmusic.ui.playlist.NewPlaylistScreen
import com.example.connectmusic.ui.search.SearchDestination
import com.example.connectmusic.ui.search.SearchScreen
import com.example.connectmusic.ui.song.PlaylistSongDestination
import com.example.connectmusic.ui.song.PlaylistSongScreen
import com.example.connectmusic.ui.song.SongDestination
import com.example.connectmusic.ui.song.SongScreen

/**
 * Navigacny graf pre aplikaciu.
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
        // HOMESCREEN
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToPlaylistEntry = { navController.navigate(NewPlaylistDestination.route) },
                navigateToSearchEntry = { navController.navigate(SearchDestination.route) },
                navigateToPlaylistDetails = { playlistId ->
                    navController.navigate("${PlaylistDetailsDestination.route}/$playlistId")
                }
            )
        }

        // NEWPLAYLISTSCREEN
        composable(route = NewPlaylistDestination.route) {
            NewPlaylistScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        // SEARCHSCREEN
        composable(route = SearchDestination.route) {
            SearchScreen(
                navigateBack = { navController.popBackStack() },
                navigateToSongDetail = { songId ->
                    navController.navigate("${SongDestination.route}/$songId")
                }
            )
        }

        // SONGSCREEN
        composable(
            route = SongDestination.routeWithArgs,
            arguments = listOf(navArgument(SongDestination.songIdArg) {
                type = NavType.IntType
            })
        ) {backStackEntry ->
                val songId = backStackEntry.arguments?.getInt(SongDestination.songIdArg) ?: return@composable

                SongScreen(
                    songId = songId,
                    navigateBack = { navController.popBackStack() }
                )
        }

        // PLAYLISTDETAILSSCREEN
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

        // PLAYLISTSONGSCREEN
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

            PlaylistSongScreen(
                songId = songId,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}