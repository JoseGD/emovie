package com.example.emovie.moviedetail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emovie.model.MovieDetail
import com.example.emovie.repositories.MoviesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by josegonzalezdamico on 01/11/2022
 */

@SuppressLint("CheckResult")
class MovieDetailViewModel: ViewModel(), KoinComponent {

    val movieDetailLiveData = MutableLiveData<MovieDetail>()
    val errorLiveData = MutableLiveData<String>()

    private val moviesRepo: MoviesRepository by inject()

    fun loadMovieDetail(movieId: Int) {
        moviesRepo.getMovieDetails(movieId).subscribe({
            movieDetailLiveData.value = it
        }, {
            errorLiveData.value = it.localizedMessage
        })
    }

}
