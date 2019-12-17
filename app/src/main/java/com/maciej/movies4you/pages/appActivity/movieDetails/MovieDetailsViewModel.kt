package com.maciej.movies4you.pages.appActivity.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.functional.data.StandardListType
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.body.RatingBody
import com.maciej.movies4you.models.body.StandardListTypeBody
import com.maciej.movies4you.models.movies.MovieDetails
import com.maciej.movies4you.models.responses.RateStatusResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject
import com.maciej.movies4you.functional.rest.Rest


class MovieDetailsViewModel : BaseViewModel() {

    private var isFavourite = MutableLiveData<Boolean>()
    private var isWatched = MutableLiveData<Boolean>()

    private var details = MutableLiveData<MovieDetails>()
    var rate = 1F
    var movieId = 0

    val observableDetails: LiveData<MovieDetails>
        get() = details

    val observableFavorite: LiveData<Boolean>
        get() = isFavourite

    val observableWatched: LiveData<Boolean>
        get() = isWatched

    @Inject
    lateinit var restInterface: RestInterface

    fun initialize(movieId: Int) {
        this.movieId = movieId
        loadMovie()
        if (SharedPrefs.getTmdbUserLogged()) {
            getMovieStatus()
        }
    }

    private fun loadMovie() {
        subscription.add(
            restInterface.getMovieDetails(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        details.value = it
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }


    private fun getMovieStatus() {
        subscription.add(
            restInterface.getMovieStatus(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        isFavourite.value = it.favorite
                        isWatched.value = it.watchlist
                        try {
                            val jsonObject = Rest.gson.toJsonTree(it.rated).asJsonObject
                            this.rate =
                                Rest.gson.fromJson(jsonObject, RateStatusResponse::class.java)
                                    .rateValue
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }


    fun rateMovie(rate: Float) {
        subscription.add(
            restInterface.rateMovie(
                movieId, Constants.API_KEY, null, SharedPrefs.getSessionId(), RatingBody(rate)
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        this.rate = rate
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }

    fun addToStandardList(requestBody: StandardListTypeBody, listType: String) {
        subscription.add(
            restInterface.modifyStandardList(
                listType,
                Constants.API_KEY,
                SharedPrefs.getSessionId(),
                requestBody
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        if (listType == StandardListType.Favorite.queryName) {
                            isFavourite.value = requestBody.favorite
                        } else {
                            isWatched.value = requestBody.watchlist
                        }
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }

}