package com.agus.kitabisatestproject.ui.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.agus.kitabisatestproject.R
import com.agus.kitabisatestproject.model.Review
import com.agus.kitabisatestproject.ui.movie.adapter.ReviewAdapter
import com.agus.kitabisatestproject.util.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_review_list.*

class MovieReviewActivity : AppCompatActivity(), MovieReviewListener, SwipeRefreshLayout.OnRefreshListener {

    private var id = 0
    private lateinit var presenter: MovieReviewPresenter
    private lateinit var reviewAdapter: ReviewAdapter
    private var page = 1
    private var isLoading = false
    private var isLastPage = false

    companion object{

        const val MOVIE_ID = "movie_id"
        const val MOVIE_TITLE = "movie_title"

        @JvmStatic
        fun generateIntent(context: Context, movieId: Int, title: String): Intent{
            return Intent(context, MovieReviewActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
                putExtra(MOVIE_TITLE, title)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)
        setSupportActionBar(toolbar)

        supportActionBar?.title = ""

        txtTitle.text = intent.getStringExtra(MOVIE_TITLE)
        swipeLayout.setOnRefreshListener(this)

        presenter = MovieReviewPresenter(this)

        id =intent.getIntExtra(MOVIE_ID, 0)

        presenter.getReviews(id, page)

        prepareRecycler()
    }

    private fun prepareRecycler(){
        reviewAdapter = ReviewAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        recycler_review.layoutManager = layoutManager
        recycler_review.adapter = reviewAdapter
        recycler_review.addOnScrollListener(object: PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                this@MovieReviewActivity.isLoading = true
                Handler().postDelayed({
                    this@MovieReviewActivity.reviewAdapter.setLoading()
                    presenter.getReviews(id, page)
                },1000)
            }

            override fun isLastPage(): Boolean {
                return this@MovieReviewActivity.isLastPage
            }

            override fun isLoading(): Boolean {
                return this@MovieReviewActivity.isLoading
            }

        })
    }

    override fun onSuccessGetReview(page: Int, reviews: List<Review>) {
        if(reviews.isNotEmpty()){
            this.page = page+1
            isLoading = false
            isLastPage = false
            reviewAdapter.removeLoading()
            reviewAdapter.addAll(reviews)

        }
        else{
            isLoading = false
            isLastPage = true
            reviewAdapter.removeLoading()
        }
    }

    override fun onFailedGetReview(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        reviewAdapter.removeAll()
        this.page = 1
        this.isLastPage = false
        this.isLoading = false
        presenter.getReviews(id, page)

        Handler().postDelayed(
            { if (swipeLayout != null) swipeLayout.isRefreshing = false },
            2000
        )
    }
}
