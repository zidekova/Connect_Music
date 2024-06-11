package com.example.connectmusic.ui.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.ConnectMusicTopAppBar
import com.example.connectmusic.R
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

/**
 * Navigacna destinacia pre PlaylistDetailsScreen
 */
object PlaylistDetailsDestination : NavigationDestination {
    override val route = "playlist_details"
    override val titleRes = R.string.playlist_details_title
    const val playlistIdArg = "playlistId"
    val routeWithArgs = "$route/{$playlistIdArg}"
}

/**
 * Obrazovka detailov playlistu.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailsScreen(
    navigateBack: () -> Unit,
    navigateToSongDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlaylistDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val songNames = remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(uiState) {
        val names = viewModel.getPlaylistSong(viewModel.playlistId)
        songNames.value = names ?: emptyList()
    }

    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ConnectMusicTopAppBar(
                title = stringResource(PlaylistDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog.value = true
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_playlist_title),
                )
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        if (showDialog.value) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    coroutineScope.launch {
                        viewModel.deletePlaylist()
                        navigateBack()
                    }
                },
                onDeleteCancel = {
                    showDialog.value = false
                }
            )
        }

        PlaylistDetailsBody(
            songNames = songNames.value,
            onSongClick = { songName ->
                coroutineScope.launch {
                    navigateToSongDetails(viewModel.getIdSongFromName(songName))
                }
            },
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
        )
    }
}

/**
 * Telo obrazovky PlaylistDetailsScreen
 */
@Composable
private fun PlaylistDetailsBody(
    songNames: List<String>,
    onSongClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(dimensionResource(R.dimen.padding_small))
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (songNames.isEmpty()) {
            Text(
                text = stringResource(R.string.no_songs_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            songNames.forEach { songName ->
                SavedSong(
                    songName = songName,
                    onClick = { onSongClick(songName) }
                )
            }
        }
    }
}

/**
 * Vykreslenie ulozeneho skladby
 */
@Composable
private fun SavedSong(
    songName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .padding(dimensionResource(R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_minimum))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = songName,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

/**
 * Confirmation dialog pre odstranenie playlistu
 */
@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_playlist_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}