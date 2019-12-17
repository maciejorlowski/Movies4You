package com.maciej.movies4you.pages.appActivity.tvShowDetails

import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.functional.rest.Rest
import com.maciej.movies4you.models.responses.RateStatusResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

//private fun loadTV() =
//    restInterface.getTVDetails(movieId, Constants.API_KEY, SharedPrefs.getLanguageCode())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeOn(Schedulers.io())
//        .doOnSubscribe { onRetrieveDataStart() }
//        .doOnTerminate { onRetrieveDataFinish() }
//        .subscribe(
//            {
//                details.value = it
//            },
//            { error ->
//                onRequestError(error)
//            }
//        )
//
//private fun getTVStatus() =
//    restInterface.getTVStatus(movieId, Constants.API_KEY, SharedPrefs.getSessionId())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeOn(Schedulers.io())
//        .doOnSubscribe { onRetrieveDataStart() }
//        .doOnTerminate { onRetrieveDataFinish() }
//        .subscribe(
//            {
//                isFavourite.value = it.favorite
//                isWatched.value = it.watchlist
//
//                try {
//                    val jsonObject = Rest.gson.toJsonTree(it.rated).asJsonObject
//                    this.rate = Rest.gson.fromJson(jsonObject, RateStatusResponse::class.java).rateValue
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            },
//            { error ->
//                onRequestError(error)
//            }
//        )