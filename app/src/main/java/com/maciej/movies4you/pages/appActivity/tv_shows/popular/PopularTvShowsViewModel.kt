package com.maciej.movies4you.pages.appActivity.tv_shows.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.tv_shows.TvShow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularTvShowsViewModel : BaseViewModel() {

    private var tvShows = MutableLiveData<MutableList<TvShow>>()
    var pageNr: Int = 0

    val observableTvShows: LiveData<MutableList<TvShow>>
        get() = tvShows

    @Inject
    lateinit var restInterface: RestInterface

    init {
        tvShows.value = mutableListOf()
        loadNextData()
    }

    fun loadNextData() {
        subscription.plusAssign(restInterface.getPopularTvShows(page = ++pageNr)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    tvShows.value?.addAll(it.tvShows.filterNotNull())
                    tvShows.value = tvShows.value
                },
                { error ->
                    onRequestError(error)
                }
            ))
    }

}