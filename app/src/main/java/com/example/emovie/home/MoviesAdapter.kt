package com.example.emovie.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emovie.databinding.MovieRowBinding
import com.example.emovie.model.ListedMovie

/**
 * Created by josegonzalezdamico on 23/10/2022
 */

class MoviesAdapter(private val upcomingList: List<ListedMovie>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(MovieRowBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviesViewHolder).bindData(upcomingList[position])
    }

    override fun getItemCount() = upcomingList.size

    class MoviesViewHolder(private val binding: MovieRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(member: ListedMovie) {
            Glide.with(binding.moviePoster)
                .load(PATH_PREFIX + member.posterPath)
                .into(binding.moviePoster)
        }

        companion object {
            private const val PATH_PREFIX = "https://image.tmdb.org/t/p/w500"
        }

    }

}
