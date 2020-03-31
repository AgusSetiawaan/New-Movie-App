package com.agus.kitabisatestproject.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agus.kitabisatestproject.util.Constant
import com.agus.kitabisatestproject.util.DateUtils
import com.agus.kitabisatestproject.R
import com.agus.kitabisatestproject.model.MovieItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_list_movie.view.*

class MovieAdapter (private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var movies: MutableList<MovieItem> = mutableListOf()
    private val movieItemView = 0
    private val loadItemView = 1
    lateinit var onItemClickListener: (MovieItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            movieItemView -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_list_movie, parent, false)
                ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
                LoadViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies[position] == MovieItem()) {
            this.loadItemView
        } else {
            this.movieItemView
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.bindTo(context, movies[position])
            holder.setOnItemClick(movies[position], onItemClickListener)
        }
    }

    private fun add(movie: MovieItem) {
        if (!this.movies.contains(movie)) {
            this.movies.add(movie)
            notifyItemInserted(movies.size - 1)
        }
    }

    fun addAll(movies: List<MovieItem>) {
        for (movieItem in movies) {
            add(movieItem)
        }
    }

    fun setData(movies: List<MovieItem>){
        this.movies = movies as MutableList<MovieItem>
        notifyDataSetChanged()
    }

    fun removeAll() {
        for (i in this.movies.indices) {
            this.movies.removeAt(0)
            notifyItemRemoved(0)
        }
    }

    fun setLoading() {
        this.movies.add(MovieItem())
        notifyItemInserted(this.movies.size - 1)
    }

    fun removeLoading() {
        if (movies.isNotEmpty()) {
            movies.removeAt(movies.size - 1)
            notifyItemRemoved(movies.size - 1)
        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindTo(context: Context, movie: MovieItem){
            with(itemView){
                Glide.with(context)
                    .load(Constant.IMAGE_URL +movie.posterPath)
                    .apply(RequestOptions()
                            .error(R.drawable.image_placeholder)
                            .placeholder(R.drawable.image_placeholder))
                    .into(movie_image)

                movie_title.text = movie.originalTitle
                release_date.text = DateUtils.format(
                    DateUtils.parseUTCTime(movie.releaseDate),
                    "dd MMMM yyyy"
                )
                description.text = movie.overview
            }
        }

        fun setOnItemClick(movie: MovieItem, onItemClick: (MovieItem) -> Unit){
            itemView.setOnClickListener {
                onItemClick(movie)
            }
        }

    }

    class LoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}