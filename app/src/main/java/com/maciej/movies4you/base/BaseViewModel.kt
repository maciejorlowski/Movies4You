package com.maciej.movies4you.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maciej.movies4you.functional.rest.Rest
import com.maciej.movies4you.functional.utils.ErrorParser
import com.maciej.movies4you.pages.appActivity.home.HomeViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.MovieDetailsViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsActors.MovieDetailsActorsViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsReviews.MovieDetailsReviewsViewModel
import com.maciej.movies4you.pages.entryActivity.login.LoginViewModel
import com.maciej.movies4you.pages.appActivity.myLists.myLists.MyListsViewModel
import com.maciej.movies4you.pages.appActivity.myLists.singleList.MySingleListViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject

abstract class BaseViewModel : BaseViewModelInjector() {

    val TAG = javaClass.simpleName

    private var errorMessage = MutableLiveData<String>()
    protected var errorCode : Int? = null
    protected var progressVisibility = MutableLiveData<Boolean>()

    val observableErrorMessage: LiveData<String>
        get() = errorMessage

    val observableProgress: LiveData<Boolean>
        get() = progressVisibility


    @Inject
    lateinit var retrofit: Retrofit

    protected var subscription: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        try {
            subscription.dispose()
        } catch (e: Exception) {
        }
    }

    protected open fun onRequestStart() {
        progressVisibility.value = true
    }

    protected open fun onRequestExecute() {
        progressVisibility.value = false
    }

    protected open fun onRequestError(error: Throwable) {
        val error = ErrorParser.parseError(error, retrofit)
        errorMessage.value = error?.message
        errorCode = error?.code
    }

    protected operator fun CompositeDisposable.plusAssign(subscribe: Disposable) {
        this.add(subscribe)
    }
}