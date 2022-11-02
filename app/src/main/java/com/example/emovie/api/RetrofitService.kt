package com.example.emovie.api

import com.example.emovie.model.MovieDetail
import com.example.emovie.model.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by josegonzalezdamico on 19/10/2022
*/

interface RetrofitService {

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Single<MovieDetail>

}
