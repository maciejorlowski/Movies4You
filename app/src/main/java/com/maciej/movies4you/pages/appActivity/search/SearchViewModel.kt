package com.maciej.movies4you.pages.appActivity.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.custom.DiscoverQueryData
import com.maciej.movies4you.models.movies.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel : BaseViewModel() {

    private var movies = MutableLiveData<MutableList<Movie>>()

    var pageNr: Int = 0

    var searchQueryData: DiscoverQueryData = DiscoverQueryData()

    val observableMovies: LiveData<MutableList<Movie>>
        get() = movies

    @Inject
    lateinit var restInterface: RestInterface

    init {
        movies.value = mutableListOf()
        loadNextMovies()
    }

    fun loadNextMovies() {
        subscription.add(restInterface.discoverMovies(
            searchQueryData.discoverType.type,
            Constants.API_KEY, SharedPrefs.getLanguageCode(),
            ++pageNr,
            searchQueryData.sortType.queryName + searchQueryData.sortType.order?.queryPrefix,
            searchQueryData.includeAdult,
            searchQueryData.minReleaseYear,
            searchQueryData.maxReleaseYear,
            searchQueryData.minVoteCount,
            searchQueryData.maxVoteCount,
            searchQueryData.minVoteAverage,
            searchQueryData.maxVoteAverage
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    movies.value?.addAll(it.movies.filterNotNull())
                    movies.value = movies.value
                },
                { error ->
                    onRequestError(error)
                }
            ))
    }

    fun changeSearchCriteria(newQueryData: DiscoverQueryData) {
        this.searchQueryData = newQueryData
        pageNr = 0
        movies.value?.clear()
        loadNextMovies()
    }


}