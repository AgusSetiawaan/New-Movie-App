package com.agus.kitabisatestproject.repo

import com.agus.kitabisatestproject.model.*
import io.reactivex.Observable

interface MovieRepository {

    fun getPopularMovies(page: Int): Observable<Pair<Int, List<MovieItem>>>
    fun getTopRatedMovies(page: Int): Observable<Pair<Int, List<MovieItem>>>
    fun getNowPlayingMovies(page: Int): Observable<Pair<Int, List<MovieItem>>>
    fun getUpcomingMovies(page: Int): Observable<Pair<Int, List<MovieItem>>>
    fun getMovieDetail(id: Int): Observable<MoviesDetail>
    fun getMovieReviews(id: Int, page: Int): Observable<Pair<Int, List<Review>>>
}