package com.agus.kitabisatestproject.ui.movie.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.agus.kitabisatestproject.util.PaginationScrollListener
import com.agus.kitabisatestproject.R
import com.agus.kitabisatestproject.model.Category
import com.agus.kitabisatestproject.model.MovieItem
import com.agus.kitabisatestproject.ui.movie.adapter.MovieAdapter
import com.agus.kitabisatestproject.ui.movie.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieListView, SwipeRefreshLayout.OnRefreshListener, BottomSheetDialog.CategoryListener{

    private lateinit var presenter: MovieListPresenter
    private lateinit var movieAdapter: MovieAdapter
    private var isLoading = false
    private var isLastPage = false
    private var page = 1
    private var category = Category.POPULAR
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MovieListPresenter(this, this)

        swipeLayout.setOnRefreshListener(this)

        presenter.getPopularMovies(page)

        prepareRecycler()

        btn_category.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.show(supportFragmentManager, "bottom-sheet-dialog")
        }
    }

    private fun prepareRecycler(){
        movieAdapter = MovieAdapter(this)
        movieAdapter.onItemClickListener = {
            startActivity(MovieDetailActivity.generateIntent(this, it.id))
        }
        val layoutManager = LinearLayoutManager(this)
        recycler_movie.layoutManager = layoutManager
        recycler_movie.adapter = movieAdapter
        recycler_movie.addOnScrollListener(object: PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if(category != Category.FAVOURITE ){
                    this@MainActivity.isLoading = true
                    Handler().postDelayed({
                        this@MainActivity.movieAdapter.setLoading()
                        getMoviesData()
                    },1000)
                }
            }

            override fun isLastPage(): Boolean {
                return this@MainActivity.isLastPage
            }

            override fun isLoading(): Boolean {
                return this@MainActivity.isLoading
            }

        })
    }

    private fun getMoviesData(){
        when(category){
            Category.POPULAR -> {
                if(page == 1){
                    movieAdapter.removeAll()
                }
                this@MainActivity.presenter.getPopularMovies(page)
            }
            Category.TOP_RATED -> {
                if(page == 1){
                    movieAdapter.removeAll()
                }
                this@MainActivity.presenter.getTopRatedMovies(page)
            }
            Category.NOW_PLAYING -> {
                if(page == 1){
                    movieAdapter.removeAll()
                }
                this@MainActivity.presenter.getNowPlayingMovies(page)
            }
            Category.UPCOMING -> {
                if(page == 1){
                    movieAdapter.removeAll()
                }
                this@MainActivity.presenter.getUpComingMovies(page)
            }
            Category.FAVOURITE -> {
                this@MainActivity.presenter.getFavouriteMovies()
            }
        }
    }

    override fun onSuccessGetMovies(page: Int, listMovie: List<MovieItem>) {
        if(listMovie.isNotEmpty()){
            this.page = page+1
            isLoading = false
            isLastPage = false
            movieAdapter.removeLoading()
            movieAdapter.addAll(listMovie)

        }
        else{
            isLoading = false
            isLastPage = true
            movieAdapter.removeLoading()

        }
    }

    override fun onSuccessGetWatchList(listMovie: List<MovieItem>) {
        if(listMovie.isNotEmpty()){
            if(category == Category.FAVOURITE){
                movieAdapter.setData(listMovie)
            }

        }
    }

    override fun onFailedGetMovies(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        this.page = 1
        this.isLastPage = false
        this.isLoading = false
        getMoviesData()
        Handler().postDelayed(
            { if (swipeLayout != null) swipeLayout.isRefreshing = false },
            2000
        )
    }

    override fun onCategorySelected(category: Category) {
        this.category = category
        this.page = 1
        this.isLastPage = false
        this.isLoading = false
        getMoviesData()
    }

    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        else{
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this,"Please click BACK again to exit",Toast.LENGTH_SHORT).show()
        }

        Handler().postDelayed( { doubleBackToExitPressedOnce = false },2000)
    }
}
