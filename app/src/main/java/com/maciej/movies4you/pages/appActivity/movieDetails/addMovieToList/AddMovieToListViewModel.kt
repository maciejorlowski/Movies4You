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

    private var fullLists: MutableList<UserList> = mutableListOf()
    private var filteredLists = MutableLiveData<MutableList<UserList>>()

    var pageNr: Int = 1

    val observableLists: LiveData<MutableList<UserList>>
        get() = filteredLists

    @Inject
    lateinit var restInterface: RestInterface


    init {
        filteredLists.value = mutableListOf()
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
                        fullLists.addAll(it.lists)
                        filteredLists.value = fullLists
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }

    fun addMovieToList(listId: Int, movieId: Int, done: (Boolean) -> Unit) {
        subscription.add(restInterface.addMovieToList(
            listId,
            ManageListContentBody(movieId)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    filteredLists.value?.find { it.id == listId }?.itemCount?.plus(1)
                    done(true)
                },
                { error ->
                    done(false)
                    onRequestError(error)
                }
            ))
    }

    fun filterLists(prefix: String) {
        if (prefix.isNotEmpty()) {
            filteredLists.value = fullLists.filter { it.listName.contains(prefix) }.toMutableList()
        } else {
            filteredLists.value = fullLists
        }
    }
}


