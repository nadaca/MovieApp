package fr.esaip.tvshowapp.data.model


import com.google.gson.annotations.SerializedName

data class TVShow(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("tv_shows")
    val tvShows: List<TvShowX>
)