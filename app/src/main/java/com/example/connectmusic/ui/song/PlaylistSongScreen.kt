package com.example.connectmusic.ui.song

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.connectmusic.R
import com.example.connectmusic.ui.AppViewModelProvider
import com.example.connectmusic.ui.navigation.NavigationDestination
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

/**
 * Obrazovka na zobrazenie detailov o skladbe a na prehranie skladby na YouTube.
 */
@Composable
fun PlaylistSongScreen(
    songId: Int,
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
