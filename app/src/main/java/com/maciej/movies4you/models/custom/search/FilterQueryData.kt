package com.maciej.movies4you.models.custom.search

import com.maciej.movies4you.functional.data.MediaType
import com.maciej.movies4you.models.movies.Category

class FilterQueryData (

    var includeAdult: Boolean? = null,
    var minReleaseYear: String? = null,
    var maxReleaseYear: String? = null,
    var minVoteCount: Int? = null,
    var maxVoteCount: Int? = null,
    var minVoteAverage: Int? = null,
    var maxVoteAverage: Int? = null,
    var categories: List<Category>? = null,
    var discoverType: MediaType = MediaType.Movie
)