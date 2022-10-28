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

}
