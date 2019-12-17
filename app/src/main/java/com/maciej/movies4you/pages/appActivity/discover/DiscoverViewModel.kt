package com.maciej.movies4you.pages.appActivity.discover

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

class DiscoverViewModel : BaseViewModel() {

    private var movies = MutableLiveData<List<Movie>>()
    var nextPageExists: Boolean = true

    val observableMovies: LiveData<List<Movie>>
        get() = movies

    @Inject
    lateinit var restInterface: RestInterface


    fun loadMovies(pageNr: Int, queryData: DiscoverQueryData) {
        subscription.add(restInterface.discoverMovies(
            queryData.discoverType.type,
            Constants.API_KEY, SharedPrefs.getLanguageCode(),
            pageNr,
            queryData.sortType?.queryName,
            queryData.includeAdult,
            queryData.minReleaseYear,
            queryData.maxReleaseYear,
            queryData.minVoteCount,
            queryData.maxVoteCount,
            queryData.minVoteAverage,
            queryData.maxVoteAverage
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    nextPageExists = it.totalPages > pageNr
                    movies.value = it.movies
                },
                { error ->
                    onRequestError(error)
                }
            ))
    }


}