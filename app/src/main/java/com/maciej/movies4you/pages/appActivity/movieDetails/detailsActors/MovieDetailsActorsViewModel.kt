package com.maciej.movies4you.pages.appActivity.movieDetails.detailsActors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.rest.RestInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsActorsViewModel : BaseViewModel() {

    private var members = MutableLiveData<List<Any>>()


    val observableMembers: LiveData<List<Any>>
        get() = members

    @Inject
    lateinit var restInterface: RestInterface


    fun loadCredits(movieId: Int) {
        subscription.add(
            restInterface.getMovieCredits(movieId, Constants.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        members.value = it.actors.plus(it.crew)
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }
}