package com.maciej.movies4you.pages.appActivity.movieDetails.addMovieToList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.body.ManageListContentBody
import com.maciej.movies4you.models.movies.UserList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddMovieToListViewModel : BaseViewModel() {


    private var lists = MutableLiveData<MutableList<UserList>>()

    var pageNr: Int = 1


    val observableLists: LiveData<MutableList<UserList>>
        get() = lists

    @Inject
    lateinit var restInterface: RestInterface


    init {
        loadLists()
    }

    private fun loadLists() {
        subscription.add(
            restInterface.getUserLists(
                Constants.API_KEY,
                SharedPrefs.getLanguageCode(),
                SharedPrefs.getSessionId(),
                pageNr
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        lists.value = mutableListOf()
                        lists.value?.addAll(it.lists)
                        lists.value = lists.value
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }

    fun addMovieToList(listId: Int, movieId: Int,done: (Boolean) -> Unit) {
        subscription.add(restInterface.addMovieToList(
            listId,
            ManageListContentBody(movieId)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    lists.value?.find { it.id == listId }?.itemCount?.plus(1)
                    done(true)
                },
                { error ->
                    done(false)
                    onRequestError(error)
                }
            ))
    }


}


