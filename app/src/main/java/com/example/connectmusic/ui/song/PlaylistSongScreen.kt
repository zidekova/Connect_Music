package com.example.connectmusic.ui.song

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.R
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
import com.example.connectmusic.ui.playlist.DeleteConfirmationDialog
import com.example.connectmusic.ui.playlist.PlaylistDetailsViewModel
import com.example.connectmusic.ui.search.SearchViewModel
import kotlinx.coroutines.launch

object PlaylistSongDestination : NavigationDestination {
    override val route = "playlist_song_entry"
    override val titleRes = R.string.song_details_title
    const val songIdArg = "songId"
    const val playlistIdArg = "playlistId"
    val routeWithArgs = "playlist_song_entry/{$songIdArg}/{$playlistIdArg}"
}

@Composable
fun PlaylistSongScreen(
    songId: Int,
    playlistId: Int,
    searchViewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModel: PlaylistDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }

    val deleteFromPlaylist: () -> Unit = {
        viewModel.viewModelScope.launch {
            viewModel.deleteSongFromPlaylist(songId)
            navigateBack()
        }
    }

    BaseSongScreen(
        songId = songId,
        searchViewModel = searchViewModel,
        navigateBack = navigateBack,
        modifier = modifier,
        content = {
            // Content specific to PlaylistSongDetailsScreen
            Button(
                onClick = { showDialog.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Odstranit z playlistu")
            }

            if (showDialog.value) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        coroutineScope.launch {
                            deleteFromPlaylist()
                            navigateBack()
                        }
                    },
                    onDeleteCancel = {
                        showDialog.value = false
                    }
                )
            }
        }
    )
}
