package com.example.emovie.model

import com.google.gson.annotations.SerializedName

/**
 * Created by josegonzalezdamico on 22/10/2022
 */

data class ListedMovie(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String
)
