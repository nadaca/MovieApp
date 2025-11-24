package fr.esaip.tvshowapp.data.model


import com.google.gson.annotations.SerializedName

data class TVDetails(
    @SerializedName("tvShow")
    val tvShow: TvShowXX
)