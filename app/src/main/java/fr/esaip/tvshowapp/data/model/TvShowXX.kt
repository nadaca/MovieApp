package fr.esaip.tvshowapp.data.model


import com.google.gson.annotations.SerializedName

data class TvShowXX(
    @SerializedName("countdown")
    val countdown: Any,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("description_source")
    val descriptionSource: String,
    @SerializedName("end_date")
    val endDate: Any,
    @SerializedName("episodes")
    val episodes: List<Episode>,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_path")
    val imagePath: String,
    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: String,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("pictures")
    val pictures: List<String>,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("rating_count")
    val ratingCount: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("youtube_link")
    val youtubeLink: Any
)