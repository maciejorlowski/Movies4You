package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.movies.Movie

class MoviesResponse (

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val movies: List<Movie?>,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int
)