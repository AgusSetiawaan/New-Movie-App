package com.agus.kitabisatestproject.ui.movie.detail

import com.agus.kitabisatestproject.model.MoviesDetail
import com.agus.kitabisatestproject.model.Review
import com.agus.kitabisatestproject.model.WatchList

interface MovieDetailView {
    fun onSuccessGetDetailMovie(movie: MoviesDetail)
    fun onFailedGetDetailMovie(throwable: Throwable)
    fun onSuccessGetReview(reviews: List<Review>)
    fun onFailedGetReview(throwable: Throwable)
    fun onSuccessGetWatchList(watchList: WatchList)
    fun onFailedGetWatchList(throwable: Throwable)
}