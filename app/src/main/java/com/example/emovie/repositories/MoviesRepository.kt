package com.example.emovie.repositories

import com.example.emovie.api.RetrofitManager
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
}

class MoviesRepositoryImpl: MoviesRepository, KoinComponent {

    private val retrofitManager: RetrofitManager by inject()

    override fun getUpcomingMovies(): Single<MoviesResponse> {
        return retrofitManager.createService()
            .getUpcomingMovies()
            .observeOn(AndroidSchedulers.mainThread())
    }

}
