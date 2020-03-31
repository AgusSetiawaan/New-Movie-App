package com.agus.kitabisatestproject.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesDetail(
    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("original_title")
    @Expose
    val original_title: String = "",
    @SerializedName("overview")
    @Expose
    val overview: String = "",
    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String = "",
    @SerializedName("runtime")
    @Expose
    val runtime: Long = 0L,
    @SerializedName("vote_average")
    @Expose
    val vote: Double = 0.0,
    @SerializedName("genres")
    @Expose
    val genres: List<Genre> = listOf()
)

data class Genre(
    @SerializedName("id")
    @Expose
    val id: Long = 0L,
    @SerializedName("name")
    @Expose
    val name: String = ""
):Serializable

data class Reviews(
    val id: Int = 0,
    val page : Int = 0,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0,
    val results: List<Review> = listOf()
)

data class Review(
    val id: String = "",
    val author: String = "",
    val content: String = "",
    val url: String = ""
)

data class Details(
    val id:Long,
    val title:String,
    val overview: String,
    val posterPath: String?,
    val runtime: Long,
    val vote: Double,
    val genres: List<Genre>,
    val isMovies: Boolean
):Serializable