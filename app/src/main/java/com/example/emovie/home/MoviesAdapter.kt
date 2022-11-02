package com.example.emovie.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emovie.databinding.MovieRowBinding
import com.example.emovie.home.MoviesAdapter.MoviesViewHolder.Companion.LIMIT
import com.example.emovie.model.ListedMovie

/**
 * Created by josegonzalezdamico on 23/10/2022
 */

class MoviesAdapter(private val upcomingList: List<ListedMovie>,
                    private val recommended: Boolean = false,
                    private val onItemClicked: (ListedMovie) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(MovieRowBinding.inflate(inflater, parent, false), recommended)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviesViewHolder).bindData(upcomingList[position]) {
            onItemClicked(it)
        }
    }

    override fun getItemCount(): Int {
        val size = upcomingList.size
        return when (recommended) {
            true -> if (size < LIMIT) size else LIMIT
            false -> size
        }
    }

    class MoviesViewHolder(private val binding: MovieRowBinding,
                           private val big: Boolean = false): RecyclerView.ViewHolder(binding.root) {

        fun bindData(movie: ListedMovie, onItemClicked: (ListedMovie) -> Unit) {
            val posterPathPrefix = PATH_PREFIX + (if (big) POSTER_SIZE_GRID else POSTER_SIZE_HORIZONTAL)
            Glide.with(binding.moviePoster)
                .load(posterPathPrefix + movie.posterPath)
                .into(binding.moviePoster)
            itemView.setOnClickListener {
                onItemClicked(movie)
            }
        }

        companion object {
            const val RECOMMENDED = true
            const val LIMIT = 6
            private const val POSTER_SIZE_HORIZONTAL = "w500"
            private const val POSTER_SIZE_GRID = "w780"
            private const val PATH_PREFIX = "https://image.tmdb.org/t/p/"
        }

    }

}
