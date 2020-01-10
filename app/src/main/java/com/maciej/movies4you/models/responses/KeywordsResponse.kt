package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.movies.Keyword

class KeywordsResponse(
    @SerializedName("page")
    var page: Int,

    @SerializedName("results")
    var keywords: List<Keyword>,

    @SerializedName("total_pages")
    var totalPages: Int,

    @SerializedName("total_results")
    var totalResults: Int
)