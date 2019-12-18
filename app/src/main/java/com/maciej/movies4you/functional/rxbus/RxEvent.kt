package com.maciej.movies4you.functional.rxbus

import com.maciej.movies4you.functional.data.SearchSortType
import com.maciej.movies4you.models.custom.DiscoverQueryData


class RxEvent {
    class EventRequestNoPermission
    class EventDiscoverMovies(val discoverQueryData: DiscoverQueryData)
    class EventCloseAddToMoviesView

    class EventSearchMoviesSort(val sortType: SearchSortType)
}