package com.maciej.movies4you.pages.appActivity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.movies.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    private var movies = MutableLiveData<MutableList<Movie>>()
    var pageNr: Int = 1

    val observableMovies: LiveData<MutableList<Movie>>
        get() = movies

    @Inject
    lateinit var restInterface: RestInterface

    init {
        movies.value = mutableListOf()
        loadNextData()
    }

    fun loadNextData() {
        subscription.plusAssign(restInterface.getPopularMovies(page =  ++pageNr)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    movies.value?.addAll(it.movies)
                    movies.value = movies.value
                },
                { error ->
                    onRequestError(error)
                }
            ))
    }
}