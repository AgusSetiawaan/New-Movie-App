package com.agus.kitabisatestproject.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesResult(
    @SerializedName("page")
    @Expose
    val page: Int = 0,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0,
    @SerializedName("results")
    @Expose
    val results: List<MovieItem>? = null
)

data class MovieItem(
    @SerializedName("original_title")
    @Expose
    val originalTitle: String = "",
    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String = "",
    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = "",
    @Expose
    val overview: String = ""
)

data class TvResult(
    @SerializedName("page")
    @Expose
    val page: Int?,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int?,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int?,
    @SerializedName("results")
    @Expose
    val results: List<TvItem>? = null
)

data class TvItem(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?
)

data class Item(
    val title: String,
    val id: Long,
    val voteAverage: Double,
    val posterPath: String?
)

enum class Category {
    POPULAR,
    TOP_RATED,
    NOW_PLAYING,
    UPCOMING,
    FAVOURITE
}