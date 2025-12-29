package com.example.jikan.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikan.data.AnimeEntity
import com.example.jikan.data.repository.AnimeRepository
import com.example.jikan.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeList = MutableStateFlow<Resource<List<AnimeEntity>>>(Resource.Loading())
    val animeList: StateFlow<Resource<List<AnimeEntity>>> = _animeList

    private val _selectedAnime = MutableStateFlow<AnimeEntity?>(null)
    val selectedAnime: StateFlow<AnimeEntity?> = _selectedAnime

    val canShowImages = true

    init {
        loadTopAnime()
    }

    fun loadTopAnime() {
        viewModelScope.launch {
            repository.getTopAnime().collect {
                _animeList.value = it
            }
        }
    }

    fun loadDetail(id: Int) {
        viewModelScope.launch {
            val result = repository.getAnimeDetail(id)

            if (result is Resource) {
                _selectedAnime.value = result.data
            } else {
                // _selectedAnime.value = result
            }
            _selectedAnime.value = result.data
        }
    }
}
