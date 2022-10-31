package com.example.emovie.api

import com.example.emovie.model.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by josegonzalezdamico on 19/10/2022
*/

interface RetrofitService {

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<MoviesResponse>

}
