package com.agus.kitabisatestproject.ui.movie.list

import android.content.Context
import com.agus.kitabisatestproject.repo.MovieRepository
import com.agus.kitabisatestproject.repo.MovieRepositoryImpl
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations



class MovieListPresenterTest {

    @Mock
    lateinit var view: MovieListView

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var presenter: MovieListPresenter

    @Captor
    lateinit var argumentCaptor: ArgumentCaptor<MovieListView>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val context = mock(Context::class.java)

        // get the presenter reference and bind with view for testing
        presenter = MovieListPresenter(context, view)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getPopularMovies() {
        presenter.getPopularMovies(1)
        verify(movieRepository, times(1)).getPopularMovies(1)
    }

    @Test
    fun getTopRatedMovies() {
    }

    @Test
    fun getNowPlayingMovies() {
    }

    @Test
    fun getUpComingMovies() {
    }

    @Test
    fun getFavouriteMovies() {
    }
}