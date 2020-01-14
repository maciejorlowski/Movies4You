package com.maciej.movies4you.models.custom.search

import com.maciej.movies4you.functional.data.SearchSortType

class DiscoverQueryData(

    var searchPrefix: String = "",
    var keywordId : Int? = null,
    var sortType: SearchSortType = SearchSortType.POPULARITY,
    var filterData: FilterQueryData = FilterQueryData()

)
