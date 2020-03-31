package com.agus.kitabisatestproject.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agus.kitabisatestproject.R
import com.agus.kitabisatestproject.model.Review
import kotlinx.android.synthetic.main.item_movie_review.*
import kotlinx.android.synthetic.main.item_movie_review.view.*

class ReviewAdapter constructor(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val reviews: MutableList<Review> = mutableListOf()
    private val reviewItemView = 0
    private val loadItemView = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            reviewItemView -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_movie_review, parent, false)
                ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
                LoadViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (reviews[position] == Review()) {
            this.loadItemView
        } else {
            this.reviewItemView
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.bindTo(context, reviews[position])
        }
    }

    fun add(review: Review) {
        if (!this.reviews.contains(review)) {
            this.reviews.add(review)
            notifyItemInserted(reviews.size - 1)
        }
    }

    fun addAll(reviews: List<Review>) {
        for (review in reviews) {
            add(review)
        }
    }

    fun removeAll() {
        for (i in this.reviews.indices) {
            this.reviews.removeAt(0)
            notifyItemRemoved(0)
        }
    }

    fun setLoading() {
        this.reviews.add(Review())
        notifyItemInserted(this.reviews.size - 1)
    }

    fun removeLoading() {
        if (reviews.isNotEmpty()) {
            reviews.removeAt(reviews.size - 1)
            notifyItemRemoved(reviews.size - 1)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindTo(context: Context, review: Review){
            with(itemView){
                reviewer_name.text = context.getString(R.string.review_by, review.author)
                review_text.text = review.content
            }
        }
    }

    class LoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}