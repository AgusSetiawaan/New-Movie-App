package com.agus.kitabisatestproject.ui.movie.detail

import android.content.Context
import android.util.Log
import com.agus.kitabisatestproject.database.AppDatabase
import com.agus.kitabisatestproject.model.MoviesDetail
import com.agus.kitabisatestproject.model.WatchList
import com.agus.kitabisatestproject.repo.MovieRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDetailPresenter constructor(context: Context, private val view: MovieDetailView) {

    private val compositeDisposable = CompositeDisposable()
    private val movieRepository = MovieRepositoryImpl()
    private val daoWatchList = AppDatabase.getAppDatabase(context).daoWatchlist()

    fun saveToFavourite(movie: MoviesDetail){
        GlobalScope.launch {
            daoWatchList.insert(WatchList(movie.id, movie.original_title, movie.overview, movie.posterPath, movie.releaseDate))
        }
    }

    fun deleteFromFavourite(idBackend: Int){
        GlobalScope.launch {
            daoWatchList.deleteByIdBackend(idBackend)
        }
    }

    fun getWatchList(idBackend: Int){
        compositeDisposable.addAll(
            daoWatchList.getDetailsMovies(idBackend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    view::onSuccessGetWatchList,
                    view::onFailedGetWatchList
                )
        )

    }

    fun getMovieDetail(id: Int){
        compositeDisposable.addAll(
            movieRepository.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    view::onSuccessGetDetailMovie,
                    view::onFailedGetDetailMovie
                )
        )
    }

    fun getReviews(id: Int, page: Int){
        compositeDisposable.addAll(
            movieRepository.getMovieReviews(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onSuccessGetReview(it.second)
                },view::onFailedGetReview

                )
        )
    }
}