package com.agus.kitabisatestproject.ui.review

import com.agus.kitabisatestproject.model.Review
import com.agus.kitabisatestproject.repo.MovieRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieReviewPresenter constructor(private val view: MovieReviewListener) {

    private val movieRepository = MovieRepositoryImpl()
    private val compositeDisposable = CompositeDisposable()

    fun getReviews(id: Int, page: Int){
        compositeDisposable.addAll(
            movieRepository.getMovieReviews(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onSuccessGetReview(it.first, it.second)
                },view::onFailedGetReview)
        )
    }
}

interface MovieReviewListener {
    fun onSuccessGetReview(page: Int, reviews: List<Review>)
    fun onFailedGetReview(throwable: Throwable)
}