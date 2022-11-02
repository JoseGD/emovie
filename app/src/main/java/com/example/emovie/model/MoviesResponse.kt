package com.example.emovie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by josegonzalezdamico on 28/10/2022
 */

data class MoviesResponse(@SerializedName("results") val movieList: List<ListedMovie>)
