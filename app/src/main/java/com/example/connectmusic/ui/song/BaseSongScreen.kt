package com.example.connectmusic.ui.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.connectmusic.ConnectMusicTopAppBar
import com.example.connectmusic.R
import com.example.connectmusic.data.tables.Song

/**
 * BaseSongScreen uchovava rovanke vlastnosti pre PlaylistSongScreen a SongScreen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseSongScreen(
    songId: Int,
    viewModel: SongViewModel,
    navigateBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
) {

    var song by remember { mutableStateOf<Song?>(null) }
    var interpret by remember { mutableStateOf<String?>(null) }
    var genre by remember { mutableStateOf<String?>(null) }
    var decade by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val retrievedSong = viewModel.getSongById(songId)
        song = retrievedSong

        retrievedSong?.let {
            val interpretResult = viewModel.getInterpretBySongId(it.id_song)
            val genreResult = viewModel.getGenreBySongId(it.id_song)
            val decadeResult = viewModel.getDecadeBySongId(it.id_song)

            interpret = interpretResult
            genre = genreResult
            decade = decadeResult
        }
    }

    Scaffold(
        topBar = {
            ConnectMusicTopAppBar(
                title = stringResource(R.string.song_details_title),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        content = { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    song?.let {
                        Text(
                            text = it.name_song,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = interpret ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = genre ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = decade ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } ?: run {
                        Text(
                            text = stringResource(R.string.song_not_found),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    content()
                }
            }
        }
    )
}
