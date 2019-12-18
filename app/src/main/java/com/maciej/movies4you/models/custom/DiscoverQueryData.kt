package com.maciej.movies4you.models.custom

import com.maciej.movies4you.functional.data.SearchSortType
import com.maciej.movies4you.functional.data.MediaType

class DiscoverQueryData(

    var sortType: SearchSortType = SearchSortType.POPULARITY,
    var includeAdult: Boolean? = null,
    var minReleaseYear: String? = null,
    var maxReleaseYear: String? = null,
    var minVoteCount: Int? = null,
    var maxVoteCount: Int? = null,
    var minVoteAverage: Int? = null,
    var maxVoteAverage: Int? = null,
    var discoverType: MediaType = MediaType.Movie

)
