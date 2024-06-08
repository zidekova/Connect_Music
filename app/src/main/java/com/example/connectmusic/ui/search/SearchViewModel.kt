package com.example.connectmusic.ui.search

import android.util.Log
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

class SearchViewModel(
    private val genreRepository: GenreRepository,
    private val interpretRepository: InterpretRepository,
    private val decadeRepository: DecadesRepository,
    private val songRepository: SongRepository
) : ViewModel() {

    private val _selectedMethod = MutableStateFlow("")
    val selectedMethod: StateFlow<String> = _selectedMethod

    private val _data = MutableStateFlow<List<String>>(emptyList())
    val data: StateFlow<List<String>> = _data

    private val _randomSong = MutableStateFlow<Song?>(null)
    val randomSong: StateFlow<Song?> = _randomSong

    fun setMethod(method: String) {
        _selectedMethod.value = method
    }

    fun loadDataForMethod(method: String) {
        viewModelScope.launch {
            Log.d("SearchViewModel", "Loading data for method: $method")
            when (method) {
                "Žáner" -> {
                    genreRepository.getAllGenresNames().collect { genres ->
                        _data.value = genres
                        Log.d("SearchViewModel", "Genres loaded: $genres")
                    }
                }
                "Interpret" -> {
                    interpretRepository.getAllInterpretsNames().collect { interprets ->
                        _data.value = interprets
                        Log.d("SearchViewModel", "Interprets loaded: $interprets")
                    }
                }
                "Obdobie" -> {
                    decadeRepository.getAllDecadesNames().collect { decades ->
                        _data.value = decades
                        Log.d("SearchViewModel", "Decades loaded: $decades")
                    }
                }
                else -> {
                    Log.d("SearchViewModel", "Invalid method")
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
                else -> emptyList() // Neznámá metoda, vrátit prázdný seznam
            }

            if (songsByMethod.isNotEmpty()) {
                val randomIndex = (0 until songsByMethod.size).random()
                songsByMethod[randomIndex]
            } else {
                null
            }
        }
    }

    suspend fun getSongById(id: Int): Song? {
        return withContext(Dispatchers.IO) {
            songRepository.getSongById(id)
        }
    }

    suspend fun getInterpretBySongId(id: Int): String? {
        return withContext(Dispatchers.IO) {
            songRepository.getInterpretBySongId(id)
        }
    }

    suspend fun getGenreBySongId(id: Int): String? {
        return withContext(Dispatchers.IO) {
            songRepository.getGenreBySongId(id)
        }
    }

    suspend fun getDecadeBySongId(id: Int): String? {
        return withContext(Dispatchers.IO) {
            songRepository.getDecadeBySongId(id)
        }
    }

    fun setRandomSong(song: Song?) {
        _randomSong.value = song
    }
}