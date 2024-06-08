package com.example.connectmusic.ui.song

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.R
import com.example.connectmusic.data.tables.Playlist
import com.example.connectmusic.data.tables.Song
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
import com.example.connectmusic.ui.playlist.PlaylistDetailsDestination
import com.example.connectmusic.ui.playlist.PlaylistDetailsDestination.playlistIdArg
import com.example.connectmusic.ui.search.SearchViewModel
import kotlinx.coroutines.launch

object SongDestination : NavigationDestination {
    override val route = "song_entry"
    override val titleRes = R.string.song_entry_title
    const val songIdArg = "songId"
    val routeWithArgs = "$route/{$songIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongScreen(
    songId: Int,
    searchViewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory),
    //navigateToPlaylistSelection: (Song) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }

    val playlists by searchViewModel.playlists.collectAsState()

    val addToPlaylist: (Playlist) -> Unit = { playlist ->
        showDialog.value = false
        searchViewModel.viewModelScope.launch {
            searchViewModel.addSongToPlaylist(songId, playlist.id_playlist)
        }
    }

    var song by remember {
        mutableStateOf<Song?>(null)
    }
    var interpret by remember {
        mutableStateOf<String?>(null)
    }
    var genre by remember {
        mutableStateOf<String?>(null)
    }
    var decade by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(Unit) {
        val retrievedSong = searchViewModel.getSongById(songId)
        song = retrievedSong

        retrievedSong?.let {
            val interpretResult = searchViewModel.getInterpretBySongId(it.id_song)
            val genreResult = searchViewModel.getGenreBySongId(it.id_song)
            val decadeResult = searchViewModel.getDecadeBySongId(it.id_song)

            interpret = interpretResult
            genre = genreResult
            decade = decadeResult
        }
    }

    Log.d("SongScreen", "Received songId: $songId")
    Log.d("SongScreen", "Retrieved song: $song")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Song Details") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                song?.let {
                    Text(text = "Name: ${it.name_song}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Interpret: ${interpret ?: "Loading..."}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Genre: ${genre ?: "Loading..."}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Decade: ${decade ?: "Loading..."}", style = MaterialTheme.typography.bodyLarge)
                } ?: run {
                    Text(text = "Song not found", style = MaterialTheme.typography.bodyLarge)
                }
                Button(
                    onClick = { showDialog.value = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Přidat do playlistu")
                }

                // Zobrazení dialogu s výběrem playlistu, pokud je showDialog true
                if (showDialog.value) {
                    PlaylistSelectionDialog(
                        playlists = playlists,
                        onPlaylistSelected = addToPlaylist,
                        onDismiss = { showDialog.value = false }
                    )
                }
            }
        }
    )
}

@Composable
fun PlaylistSelectionDialog(
    playlists: List<Playlist>,
    onPlaylistSelected: (Playlist) -> Unit,
    onDismiss: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Vyberte playlist",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
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
                Text("Zrušit")
            }
        }
    }
}

@Composable
fun PlaylistItem(playlist: Playlist, onClick: () -> Unit) {
    Text(
        text = playlist.name_playlist,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    )
}