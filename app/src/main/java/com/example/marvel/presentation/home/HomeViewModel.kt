package com.example.marvel.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.Comic
import com.example.marvel.data.ComicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlin.random.Random

interface HomeViewModel {
    val uiState: Flow<HomeUiState>
}

class DefaultHomeViewModel : HomeViewModel, ViewModel() {

    private val comicRepository = ComicRepository()

    override val uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    init {
        viewModelScope.launch {
            val comic = comicRepository.getComic(randomComicId()).last()
            uiState.value = if (comic == null) {
                HomeUiState.Error("Failed to load comic data")
            } else {
                HomeUiState.ComicLoaded(comic)
            }
        }
    }

    private fun randomComicId(): Int {
        return Random(System.currentTimeMillis()).nextInt(1, 13790)
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
    data class ComicLoaded(val comic: Comic) : HomeUiState
}
