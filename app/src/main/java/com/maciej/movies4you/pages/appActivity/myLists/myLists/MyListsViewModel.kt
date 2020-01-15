package com.maciej.movies4you.pages.appActivity.myLists.myLists


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.functional.utils.ErrorParser
import com.maciej.movies4you.models.body.ListBody
import com.maciej.movies4you.models.movies.UserList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class MyListsViewModel : BaseViewModel() {

    private var lists = MutableLiveData<MutableList<UserList>>()

    private var pageNr: Int = 1

    val observableMyLists: LiveData<MutableList<UserList>>
        get() = lists

    @Inject
    lateinit var restInterface: RestInterface

    fun init() {
        loadLists()
    }

    fun fetchNewPage() {
        pageNr++
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

    fun createNewList(body: ListBody) {
        subscription.add(
            restInterface.createNewList(Constants.API_KEY, SharedPrefs.getSessionId(), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        loadLists()
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }

    fun deleteList(list: UserList) {
        subscription.add(
            restInterface.deleteList(list.id.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        lists.value?.remove(list)
                        lists.value = lists.value
                    }, { error ->
                        onRequestError(error)
                        if (errorCode == Constants.StatusCodes.INTERNAL_ERROR) {
                            lists.value?.remove(list)
                            lists.value = lists.value
                        }
                    }
                ))
    }

    fun clearList(listId: Int) {
        subscription.add(
            restInterface.clearList(listId, Constants.API_KEY, SharedPrefs.getSessionId(), true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        lists.value?.find { it.id == listId }?.itemCount = 0
                    }, { error ->
                        onRequestError(error)
                    }
                ))
    }
}