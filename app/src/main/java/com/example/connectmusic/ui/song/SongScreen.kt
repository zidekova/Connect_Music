package com.example.connectmusic.ui.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.R
import com.example.connectmusic.data.tables.Playlist
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

/**
 * Navigacna destinacia pre SongScreen
 */
object SongDestination : NavigationDestination {
    override val route = "song_entry"
    override val titleRes = R.string.song_entry_title
    const val songIdArg = "songId"
    val routeWithArgs = "$route/{$songIdArg}"
}

@Composable
fun SongScreen(
    songId: Int,
    viewModel: SongViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }
    val playlists by viewModel.playlists.collectAsState()

    val addToPlaylist: (Playlist) -> Unit = { playlist ->
        showDialog.value = false
        viewModel.viewModelScope.launch {
            viewModel.addSongToPlaylist(songId, playlist.id_playlist)
        }
    }

    BaseSongScreen(
        songId = songId,
        viewModel = viewModel,
        navigateBack = navigateBack,
        modifier = modifier,
        content = {
            Button(
                onClick = { showDialog.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.add_to_playlist))
            }

            if (showDialog.value) {
                PlaylistSelectionDialog(
                    playlists = playlists,
                    onPlaylistSelected = addToPlaylist,
                    onDismiss = { showDialog.value = false }
                )
            }
        }
    )
}

/**
 * Selection dialog pre vyber playlistu
*/
@Composable
fun PlaylistSelectionDialog(
    playlists: List<Playlist>,
    onPlaylistSelected: (Playlist) -> Unit,
    onDismiss: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.select_playlist),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )
            playlists.forEach { playlist ->
                PlaylistItem(playlist = playlist) {
                    onPlaylistSelected(playlist)
                }
            }
            Button(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    }
}

/**
 * Playlist v selection dialogu
 */
@Composable
fun PlaylistItem(playlist: Playlist, onClick: () -> Unit) {
    Text(
        text = playlist.name_playlist,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = dimensionResource(R.dimen.padding_small))
    )
}