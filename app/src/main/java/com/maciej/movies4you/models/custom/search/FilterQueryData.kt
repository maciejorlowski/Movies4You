package com.maciej.movies4you.models.custom.search

import com.maciej.movies4you.functional.data.MediaType

class FilterQueryData (

    var includeAdult: Boolean? = null,
    var minReleaseYear: String? = null,
    var maxReleaseYear: String? = null,
    var minVoteCount: Int? = null,
    var maxVoteCount: Int? = null,
    var minVoteAverage: Int? = null,
    var maxVoteAverage: Int? = null,
    var discoverType: MediaType = MediaType.Movie
)