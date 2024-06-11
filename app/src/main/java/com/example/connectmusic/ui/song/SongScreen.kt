package com.example.connectmusic.ui.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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

/**
 * Obrazovka na zobrazenie detailov o skladbe a na ulozenie skladby do playlistu.
 */
@Composable
fun SongScreen(
    songId: Int,
    viewModel: SongViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val playlists by viewModel.playlists.collectAsState()

    val addToPlaylist: (Playlist) -> Unit = { playlist ->
        showDialog = false
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
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.add_to_playlist))
            }

            if (showDialog) {
                PlaylistSelectionDialog(
                    playlists = playlists,
                    onPlaylistSelected = addToPlaylist,
                    onDismiss = { showDialog = false }
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
    var selectedPlaylist by remember { mutableStateOf<Playlist?>(null) }

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
                PlaylistItem(
                    playlist = playlist,
                    isSelected = (playlist == selectedPlaylist)
                ) {
                    selectedPlaylist = playlist
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        selectedPlaylist?.let {
                            onPlaylistSelected(it)
                        }
                    },
                    enabled = (selectedPlaylist != null),
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
                ) {
                    Text(stringResource(R.string.ok))
                }
                Button(
                    onClick = onDismiss,
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}

/**
 * Playlist v selection dialogu
 */
@Composable
fun PlaylistItem(playlist: Playlist, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = playlist.name_playlist,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.clickable { onClick() }
        )
    }
}