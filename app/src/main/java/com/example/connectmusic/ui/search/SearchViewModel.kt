package com.example.connectmusic.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectmusic.data.repositories.DecadesRepository
import com.example.connectmusic.data.repositories.GenreRepository
import com.example.connectmusic.data.repositories.InterpretRepository
import com.example.connectmusic.data.repositories.SongRepository
import com.example.connectmusic.data.tables.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * View model pre SearchScreen.
 * Ziskava zoznam zanrov, interpretov, obdobi a skladieb z databazy.
 */
class SearchViewModel(
    private val genreRepository: GenreRepository,
    private val interpretRepository: InterpretRepository,
    private val decadeRepository: DecadesRepository,
    private val songRepository: SongRepository
) : ViewModel() {

    private val _data = MutableStateFlow<List<String>>(emptyList())
    val data: StateFlow<List<String>> = _data

    private val _randomSong = MutableStateFlow<Song?>(null)

    fun loadDataForMethod(method: String) {
        viewModelScope.launch {
            when (method) {
                "Žáner" -> {
                    genreRepository.getAllGenresNames().collect { genres ->
                        _data.value = genres
                    }
                }
                "Interpret" -> {
                    interpretRepository.getAllInterpretsNames().collect { interprets ->
                        _data.value = interprets
                    }
                }
                "Obdobie" -> {
                    decadeRepository.getAllDecadesNames().collect { decades ->
                        _data.value = decades
                    }
                }
                else -> {
                    _data.value = emptyList()
                }
            }
        }
    }

    suspend fun getRandomSong(method: String, option: String): Song? {
        return withContext(Dispatchers.IO) {
            val songsByMethod = when (method) {
                "Žáner" -> songRepository.getSongsByGenre(option)
                "Interpret" -> songRepository.getSongsByInterpret(option)
                "Obdobie" -> songRepository.getSongsByDecade(option)
                else -> emptyList()
            }

            if (songsByMethod.isNotEmpty()) {
                val randomIndex = (0 until songsByMethod.size).random()
                songsByMethod[randomIndex]
            } else {
                null
            }
        }
    }

    fun setRandomSong(song: Song?) {
        _randomSong.value = song
    }
}
