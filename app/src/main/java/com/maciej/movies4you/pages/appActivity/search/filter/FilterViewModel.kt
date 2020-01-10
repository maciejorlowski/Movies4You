package com.maciej.movies4you.pages.appActivity.search.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.movies.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FilterViewModel : BaseViewModel() {

    private var categories = MutableLiveData<List<Category>>()

    val observableCategories: LiveData<List<Category>>
        get() = categories

    @Inject
    lateinit var restInterface: RestInterface

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        subscription.add(restInterface.getMovieCategories().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe({
                categories.value = it.categories
            },
                { error ->
                    onRequestError(error)
                }
            ))
    }
}