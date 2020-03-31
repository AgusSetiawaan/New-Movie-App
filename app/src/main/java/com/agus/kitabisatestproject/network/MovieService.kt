package com.agus.kitabisatestproject.network

import com.agus.kitabisatestproject.model.MoviesDetail
import com.agus.kitabisatestproject.model.MoviesResult
import com.agus.kitabisatestproject.model.Review
import com.agus.kitabisatestproject.model.Reviews
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") key:String?,
        @Query("language") language:String?,
        @Query("page") page:Int): Observable<MoviesResult>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") key:String?,
        @Query("language") language:String?,
        @Query("page") page:Int): Observable<MoviesResult>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") key:String?,
        @Query("language") language:String?,
        @Query("page") page:Int): Observable<MoviesResult>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") key:String?,
        @Query("language") language:String?,
        @Query("page") page:Int): Observable<MoviesResult>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") id:Int,
        @Query("api_key") key:String?,
        @Query("language") language:String?): Observable<MoviesDetail>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") id:Int,
        @Query("api_key") key:String?,
        @Query("page") page:Int): Observable<Reviews>

}