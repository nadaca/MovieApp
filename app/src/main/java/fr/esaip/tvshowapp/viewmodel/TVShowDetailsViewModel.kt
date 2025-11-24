package fr.esaip.tvshowapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.esaip.tvshowapp.data.TVShowRepository
import fr.esaip.tvshowapp.data.UiState
import fr.esaip.tvshowapp.data.model.TvShowXX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowDetailsViewModel @Inject constructor(
    private val tvShowRepository: TVShowRepository) : ViewModel(){

    private var _tvShowDetailsState = MutableStateFlow<UiState<TvShowXX>>(UiState.Loading)
    val tvShowDetailsState = _tvShowDetailsState.asStateFlow()

    fun getTVShowDetails(id: Int) = viewModelScope.launch {
        tvShowRepository.getTVShowDetails(id)
            .onStart { _tvShowDetailsState.value = UiState.Loading }
            .catch { e -> _tvShowDetailsState.value = UiState.Error(e.message ?: "Erreur lors du chargement des détails") }
            .collectLatest { tvShowDetail ->
                _tvShowDetailsState.value = UiState.Success(tvShowDetail)
            }
    }
}
