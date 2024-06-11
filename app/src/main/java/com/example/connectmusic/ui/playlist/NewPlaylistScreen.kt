package com.example.connectmusic.ui.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.ConnectMusicTopAppBar
import com.example.connectmusic.R
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

/**
 * Navigacna destinacia pre NewPlaylistScreen
 */
object NewPlaylistDestination : NavigationDestination {
    override val route = "playlist_entry"
    override val titleRes = R.string.playlist_entry_title
}

/**
 * Obrazovka na vytvorenie noveho playlistu.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlaylistScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: NewPlaylistViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ConnectMusicTopAppBar(
                title = stringResource(NewPlaylistDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
    ) { innerPadding ->
        NewPlaylistBody(
            playlistUiState = viewModel.playlistUiState,
            onPlaylistValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.savePlaylist()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

/**
 * Telo obrazovky NewPlaylistScreen
 */
@Composable
fun NewPlaylistBody(
    playlistUiState: PlaylistUiState,
    onPlaylistValueChange: (PlaylistDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))
    ) {
        PlaylistInputForm(
            playlistDetails = playlistUiState.playlistDetails,
            onValueChange = onPlaylistValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = playlistUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}

/**
 * InputForm na vlozenie nazvu pre novy playlist
 */
@Composable
fun PlaylistInputForm(
    playlistDetails: PlaylistDetails,
    modifier: Modifier = Modifier,
    onValueChange: (PlaylistDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = playlistDetails.namePlaylist,
            onValueChange = { onValueChange(playlistDetails.copy(namePlaylist = it)) },
            label = { Text(stringResource(R.string.playlist_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
