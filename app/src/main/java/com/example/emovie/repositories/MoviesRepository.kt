package com.example.emovie.repositories

import com.example.emovie.api.RetrofitManager
import com.example.emovie.model.MovieDetail
import com.example.emovie.model.MoviesResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by josegonzalezdamico on 23/10/2022
 */

interface MoviesRepository {
    fun getUpcomingMovies(): Single<MoviesResponse>
    fun getTopRatedMovies(): Single<MoviesResponse>
    fun getMovieDetails(movieId: Int): Single<MovieDetail>
}

class MoviesRepositoryImpl: MoviesRepository, KoinComponent {

    private val retrofitManager: RetrofitManager by inject()

    override fun getUpcomingMovies(): Single<MoviesResponse> {
        return retrofitManager.createService()
            .getUpcomingMovies()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTopRatedMovies(): Single<MoviesResponse> {
        return retrofitManager.createService()
            .getTopRatedMovies()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetail> {
        return retrofitManager.createService()
            .getMovieDetails(movieId)
            .observeOn(AndroidSchedulers.mainThread())
    }

}
