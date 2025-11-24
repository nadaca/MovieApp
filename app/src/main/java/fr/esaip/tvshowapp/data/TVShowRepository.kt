package fr.esaip.tvshowapp.data

import fr.esaip.tvshowapp.data.model.TvShowX
import fr.esaip.tvshowapp.data.model.TvShowXX
import fr.esaip.tvshowapp.data.service.TVShowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TVShowRepository @Inject constructor(private val tvShowApi: TVShowApi) {

    fun getTVShows(page: Int = 1): Flow<List<TvShowX>> = flow {
        emit(tvShowApi.getTVShows(page).tvShows)
    }

    fun getTVShowDetails(id: Int): Flow<TvShowXX> = flow {
        emit(tvShowApi.getTVShowDetails(id).tvShow)
    }

    fun searchTVShows(query: String, page: Int = 1): Flow<List<TvShowX>> = flow {
        emit(tvShowApi.searchTVShows(query, page).tvShows)
    }

}