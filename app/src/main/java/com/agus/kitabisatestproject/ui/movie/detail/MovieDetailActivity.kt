package com.agus.kitabisatestproject.ui.movie.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agus.kitabisatestproject.util.Constant
import com.agus.kitabisatestproject.util.DateUtils
import com.agus.kitabisatestproject.R
import com.agus.kitabisatestproject.model.MoviesDetail
import com.agus.kitabisatestproject.model.Review
import com.agus.kitabisatestproject.model.WatchList
import com.agus.kitabisatestproject.ui.review.MovieReviewActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.item_movie_review.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    private lateinit var presenter: MovieDetailPresenter
    private var id = 0
    private var movie: MoviesDetail = MoviesDetail()
    private var isChecked = false

    companion object{
        const val MOVIE_ID = "movie_id"

        @JvmStatic
        fun generateIntent(context: Context, id: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ID, id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        presenter = MovieDetailPresenter(this,this)

        id =intent.getIntExtra(MOVIE_ID, 0)

        presenter.getMovieDetail(id)
        presenter.getReviews(id, 1)
        presenter.getWatchList(id)

        bindButton()

    }

    private fun bindButton(){
        button_favorite.setOnClickListener {
            if(isChecked){
                presenter.deleteFromFavourite(id)
            }
            else{
                presenter.saveToFavourite(movie)
            }
            isChecked = !isChecked
        }

        btn_see_all_review.setOnClickListener {
            startActivity(MovieReviewActivity.generateIntent(this, id, movie.original_title))
        }
    }

    private fun bindDetailMovie(movie: MoviesDetail){
        Glide.with(this)
            .load(Constant.IMAGE_URL +movie.posterPath)
            .apply(
                RequestOptions()
                .error(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder))
            .into(movie_image)

        movie_title.text = movie.original_title
        release_date.text = DateUtils.format(
            DateUtils.parseUTCTime(movie.releaseDate), "dd MMMM yyyy"
        )
        description.text = movie.overview
    }

    private fun bindReview(review: Review){
        reviewer_name.text = getString(R.string.review_by, review.author)
        review_text.text = review.content
    }

    override fun onSuccessGetDetailMovie(movie: MoviesDetail) {
        this.movie = movie
        bindDetailMovie(movie)
    }

    override fun onFailedGetDetailMovie(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessGetReview(reviews: List<Review>) {
        if(reviews.isNotEmpty()){
            bindReview(reviews[0])
            layout_review.visibility = View.VISIBLE
            txt_review_empty.visibility = View.GONE
        }
        else{
            layout_review.visibility = View.GONE
            txt_review_empty.visibility = View.VISIBLE
        }

    }

    override fun onFailedGetReview(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessGetWatchList(watchList: WatchList) {
        if(watchList != null){
            isChecked = true
            button_favorite.isChecked = true
        }
    }

    override fun onFailedGetWatchList(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}