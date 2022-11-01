package com.example.emovie.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emovie.model.ListedMovie
import com.example.emovie.repositories.MoviesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by josegonzalezdamico on 23/10/2022
 */

class HomeViewModel: ViewModel(), KoinComponent {

    val upcomingMoviesLiveData = MutableLiveData<List<ListedMovie>>()
    val topRatedMoviesLiveData = MutableLiveData<List<ListedMovie>>()
    val recommendedMoviesLiveData = MutableLiveData<List<ListedMovie>>()
    val errorLiveData = MutableLiveData<String>()

    private val moviesRepo: MoviesRepository by inject()

    @SuppressLint("CheckResult")
    fun loadUpcomingMovies() {
        moviesRepo.getUpcomingMovies().subscribe({
            upcomingMoviesLiveData.value = it.movieList
        }, {
            errorLiveData.value = it.localizedMessage
        })
    }

    @SuppressLint("CheckResult")
    fun loadTopRatedMovies() {
        moviesRepo.getTopRatedMovies().subscribe({
            topRatedMoviesLiveData.value = it.movieList
            recommendedMoviesLiveData.value = it.movieList
        }, {
            errorLiveData.value = it.localizedMessage
        })
    }

    @SuppressLint("CheckResult")
    fun filterMoviesByLanguage(lang: String) {
        // TODO: get this list from cache
        moviesRepo.getTopRatedMovies().subscribe({
            recommendedMoviesLiveData.value =
                if (lang.isEmpty()) it.movieList  // This resets the filter
                else it.movieList.filter { movie -> movie.originalLanguage == lang }
        }, {
            errorLiveData.value = it.localizedMessage
        })
    }

}
