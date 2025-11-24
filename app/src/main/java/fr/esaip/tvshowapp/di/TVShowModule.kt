package fr.esaip.tvshowapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.esaip.tvshowapp.data.TVShowRepository
import fr.esaip.tvshowapp.data.service.TVShowApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TVShowModule {

    @Provides
    @Singleton
    fun provideTVShowApi(): TVShowApi {
        return Retrofit.Builder()
            .baseUrl("https://www.episodate.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TVShowApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTVShowRepository(tvShowApi: TVShowApi): TVShowRepository {
        return TVShowRepository(tvShowApi)
    }
}