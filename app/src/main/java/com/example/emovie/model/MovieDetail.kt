package com.example.emovie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by josegonzalezdamico on 01/11/2022
 */

data class MovieDetail(
    @SerializedName("original_title") val title: String,
    @SerializedName("overview") val moviePlot: String,
    @SerializedName("original_language") val language: String,
    @SerializedName("genres") val genreList: List<MovieGenre>,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAvg: Float)
