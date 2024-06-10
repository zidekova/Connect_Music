package com.example.connectmusic.ui.song

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
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
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.R
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
import com.example.connectmusic.ui.playlist.DeleteConfirmationDialog
import com.example.connectmusic.ui.playlist.PlaylistDetailsViewModel
import com.example.connectmusic.ui.search.SearchViewModel
import kotlinx.coroutines.launch

/**
 * Navigacna destinacia pre PlaylistSongScreen
 */
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
    viewModel: SongViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    BaseSongScreen(
        songId = songId,
        viewModel = viewModel,
        navigateBack = navigateBack,
        modifier = modifier,
        content = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        val interpretName = viewModel.getInterpretBySongId(songId)
                        val songName = viewModel.getSongNameById(songId)
                        searchSongOnYouTube(context, interpretName, songName)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.youtube_search))
            }
        }
    )
}
/**
 * Funkcia na vytvorenie Youtube vyhladavacieho URL
 */
fun createYouTubeSearchUrl(interpretName: String, songName: String): String {
    val query = "$interpretName $songName"
    return "https://www.youtube.com/results?search_query=${Uri.encode(query)}"
}

/**
 * Funkcia na spustenie vyhladavania piesne na Youtube
 */
fun searchSongOnYouTube(context: Context, interpretName: String, songName: String) {
    val query = createYouTubeSearchUrl(interpretName, songName)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
    context.startActivity(intent)
}
