package com.agus.kitabisatestproject.repo

import com.agus.kitabisatestproject.model.MovieItem
import com.agus.kitabisatestproject.model.MoviesDetail
import com.agus.kitabisatestproject.model.Review
import com.agus.kitabisatestproject.network.NetworkClient
import com.agus.kitabisatestproject.util.Constant
import io.reactivex.Observable

class MovieRepositoryImpl : MovieRepository {

    override fun getPopularMovies(page: Int): Observable<Pair<Int, List<MovieItem>>> {
        return NetworkClient.getService()
            .getPopularMovies(Constant.AUTH, Constant.LANGUANGE, page)
            .map { return@map Pair(it.page, it.results!!)}
    }

    override fun getTopRatedMovies(page: Int): Observable<Pair<Int, List<MovieItem>>> {
        return NetworkClient.getService()
            .getTopRatedMovies(Constant.AUTH, Constant.LANGUANGE, page)
            .map { return@map Pair(it.page, it.results!!)}
    }

    override fun getNowPlayingMovies(page: Int): Observable<Pair<Int, List<MovieItem>>> {
        return NetworkClient.getService()
            .getNowPlayingMovies(Constant.AUTH, Constant.LANGUANGE, page)
            .map { return@map Pair(it.page, it.results!!)}
    }

    override fun getUpcomingMovies(page: Int): Observable<Pair<Int, List<MovieItem>>> {
        return NetworkClient.getService()
            .getUpcomingMovies(Constant.AUTH, Constant.LANGUANGE, page)
            .map { return@map Pair(it.page, it.results!!)}
    }

    override fun getMovieDetail(id: Int): Observable<MoviesDetail> {
        return NetworkClient.getService()
            .getDetailMovie(id, Constant.AUTH, Constant.LANGUANGE)
    }

    override fun getMovieReviews(id: Int, page: Int): Observable<Pair<Int, List<Review>>> {
        return NetworkClient.getService()
            .getMovieReviews(id, Constant.AUTH, page).map { return@map Pair(it.page, it.results) }
    }

}