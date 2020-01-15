package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.tv_shows.TvShow

class TvShowsResponse (

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val tvShows: List<TvShow?>,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int
)