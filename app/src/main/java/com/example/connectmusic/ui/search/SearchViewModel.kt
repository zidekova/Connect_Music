package com.example.connectmusic.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.connectmusic.data.repositories.DecadeRepository
import com.example.connectmusic.data.repositories.GenreRepository
import com.example.connectmusic.data.repositories.InterpretRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface SearchMethodListener {
    fun onMethodChanged(method: String)
}

class SearchViewModel(
    private val genreRepository: GenreRepository,
    private val interpretRepository: InterpretRepository,
    //private val decadeRepository: DecadeRepository
) : ViewModel() {

    private var searchMethodListener: SearchMethodListener? = null
    private val _data = MutableStateFlow<List<String>>(emptyList())
    val data: StateFlow<List<String>> = _data

    fun setSearchMethodListener(listener: SearchMethodListener) {
        searchMethodListener = listener
    }

    fun setMethod(method: String) {
        searchMethodListener?.onMethodChanged(method)
    }

    fun loadDataForMethod(method: String) {
        viewModelScope.launch {
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
                    }
                }
                "Obdobie" -> {
//                    decadeRepository.getAllDecadesNames().collect { decades ->
//                        _data.value = decades
//                    }
                }
                else -> {
                    Log.d("SearchViewModel", "Invalid method")
                    _data.value = emptyList()
                }
            }
        }
    }
}

data class SearchUiState (
    val method: String = ""
)