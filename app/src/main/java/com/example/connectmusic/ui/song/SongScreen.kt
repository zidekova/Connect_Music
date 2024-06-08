package com.example.connectmusic.ui.song

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.R
import com.example.connectmusic.data.tables.Song
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
import com.example.connectmusic.ui.playlist.PlaylistDetailsDestination
import com.example.connectmusic.ui.playlist.PlaylistDetailsDestination.playlistIdArg
import com.example.connectmusic.ui.search.SearchViewModel

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
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            }
        }
    )
}
