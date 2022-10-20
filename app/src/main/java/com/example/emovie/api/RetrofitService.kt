package com.example.emovie.api

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by josegonzalezdamico on 19/10/2022
*/

interface RetrofitService {

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<List<String>>  // TODO: Replace with model

}
