package com.agus.kitabisatestproject.ui.movie.list

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.agus.kitabisatestproject.database.AppDatabase
import com.agus.kitabisatestproject.model.MovieItem
import com.agus.kitabisatestproject.model.WatchList
import com.agus.kitabisatestproject.repo.MovieRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieListPresenter constructor(private val context: Context, private val view: MovieListView) {

    private val compositeDisposable = CompositeDisposable()
    private val movieRepository = MovieRepositoryImpl()
    private val daoWatchList = AppDatabase.getAppDatabase(context).daoWatchlist()

    fun getPopularMovies(page: Int){
        compositeDisposable.addAll(
            movieRepository.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onSuccessGetMovies(it.first, it.second)
                },
                    view::onFailedGetMovies
                )
        )
    }

    fun getTopRatedMovies(page: Int){
        compositeDisposable.addAll(
            movieRepository.getTopRatedMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onSuccessGetMovies(it.first, it.second)
                },
                    view::onFailedGetMovies
                )
        )
    }

    fun getNowPlayingMovies(page: Int){
        compositeDisposable.addAll(
            movieRepository.getNowPlayingMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onSuccessGetMovies(it.first, it.second)
                },
                    view::onFailedGetMovies
                )
        )
    }

    fun getUpComingMovies(page: Int){
        compositeDisposable.addAll(
            movieRepository.getUpcomingMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onSuccessGetMovies(it.first, it.second)
                },
                    view::onFailedGetMovies
                )
        )
    }

    fun getFavouriteMovies(){
        daoWatchList.getAllWatchList().observe(context as LifecycleOwner, Observer<List<WatchList>> { list ->
            val listMovie: MutableList<MovieItem> = mutableListOf()
            for(movie in list){
                listMovie.add(MovieItem(movie.title,movie.idBackend, movie.releaseDate, movie.posterPath, movie.overview))
            }
            view.onSuccessGetWatchList(listMovie)
        })
    }
}
