package com.maciej.movies4you.pages.appActivity.search.suggestions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.movies.Keyword
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchSuggestionViewModel : BaseViewModel() {

    private var keywords = MutableLiveData<MutableList<Keyword>>()

    val observableKeywords: LiveData<MutableList<Keyword>>
        get() = keywords

    private var page: Int = 1
    private var prefix = ""

    @Inject
    lateinit var restInterface: RestInterface

    fun initialize(prefix: String) {
        keywords.value = mutableListOf()
        this.prefix = prefix
        loadKeywords()
    }

    fun loadKeywords() {
        subscription.add(restInterface.getSearchKeywords(
            prefix = prefix,
            page = page++
        ).observeOn(
            AndroidSchedulers.mainThread()
        )
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe({
                keywords.value?.addAll(it.keywords)
                keywords.value = keywords.value
            }, {
                onRequestError(it)
            })
        )
    }
}