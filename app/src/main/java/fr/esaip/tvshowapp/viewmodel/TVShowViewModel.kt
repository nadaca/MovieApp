package fr.esaip.tvshowapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.esaip.tvshowapp.data.TVShowRepository
import fr.esaip.tvshowapp.data.UiState
import fr.esaip.tvshowapp.data.model.TvShowX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowViewModel @Inject constructor(
    private val tvShowRepository: TVShowRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private var _tvShowsState = MutableStateFlow<UiState<List<TvShowX>>>(UiState.Loading)
    val tvShowsState = _tvShowsState.asStateFlow()

    private var _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var _currentPage = MutableStateFlow(savedStateHandle.get<Int>("current_page") ?: 1)
    val currentPage = _currentPage.asStateFlow()

    private var currentSearchQuery: String = savedStateHandle.get("search_query") ?: ""

    init {
        // Restaurer l'état précédent si disponible
        val savedPage = savedStateHandle.get<Int>("current_page") ?: 1
        val savedQuery = savedStateHandle.get<String>("search_query") ?: ""
        
        if (savedQuery.isNotBlank()) {
            _isSearching.value = true
            searchPage(savedQuery, savedPage)
        } else {
            getTVShows(savedPage)
        }
    }

    fun getTVShows(page: Int = 1) = viewModelScope.launch {
        _currentPage.value = page
        savedStateHandle["current_page"] = page
        savedStateHandle["search_query"] = ""
        tvShowRepository.getTVShows(page)
            .onStart { _tvShowsState.value = UiState.Loading }
            .catch { e -> _tvShowsState.value = UiState.Error(e.message ?: "Erreur inconnue") }
            .collectLatest { tvShowList ->
                _tvShowsState.value = UiState.Success(tvShowList)
            }
    }

    fun searchTVShows(query: String) = viewModelScope.launch {
        currentSearchQuery = query
        savedStateHandle["search_query"] = query
        _currentPage.value = 1
        savedStateHandle["current_page"] = 1
        if (query.isBlank()) {
            getTVShows()
        } else {
            tvShowRepository.searchTVShows(query, 1)
                .onStart { _tvShowsState.value = UiState.Loading }
                .catch { e -> _tvShowsState.value = UiState.Error(e.message ?: "Erreur de recherche") }
                .collectLatest { tvShowList ->
                    _tvShowsState.value = UiState.Success(tvShowList)
                }
        }
    }

    fun nextPage() {
        val newPage = _currentPage.value + 1
        if (currentSearchQuery.isBlank()) {
            getTVShows(newPage)
        } else {
            searchPage(currentSearchQuery, newPage)
        }
    }

    fun previousPage() {
        if (_currentPage.value > 1) {
            val newPage = _currentPage.value - 1
            if (currentSearchQuery.isBlank()) {
                getTVShows(newPage)
            } else {
                searchPage(currentSearchQuery, newPage)
            }
        }
    }

    private fun searchPage(query: String, page: Int) = viewModelScope.launch {
        _currentPage.value = page
        savedStateHandle["current_page"] = page
        savedStateHandle["search_query"] = query
        tvShowRepository.searchTVShows(query, page)
            .onStart { _tvShowsState.value = UiState.Loading }
            .catch { e -> _tvShowsState.value = UiState.Error(e.message ?: "Erreur de recherche") }
            .collectLatest { tvShowList ->
                _tvShowsState.value = UiState.Success(tvShowList)
            }
    }

    fun toggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            currentSearchQuery = ""
            savedStateHandle["search_query"] = ""
            getTVShows()
        }
    }
}