package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.details.Review

class MovieReviewsResponse (

    @SerializedName("id")
    val id: Int,

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<Review>,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int

)