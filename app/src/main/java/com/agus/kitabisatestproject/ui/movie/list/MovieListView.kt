package com.agus.kitabisatestproject.ui.movie.list

import com.agus.kitabisatestproject.model.MovieItem

interface MovieListView {

    fun onSuccessGetMovies(page: Int, listMovie: List<MovieItem>)
    fun onFailedGetMovies(throwable: Throwable)
    fun onSuccessGetWatchList(listMovie: List<MovieItem>)
}