package com.maciej.movies4you.pages.appActivity.movieDetails.detailsReviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.details.Review
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsReviewsViewModel : BaseViewModel() {

    private var reviews = MutableLiveData<List<Review>>()

    val observableReviews: LiveData<List<Review>>
        get() = reviews

    @Inject
    lateinit var restInterface: RestInterface

    fun loadReviews(movieId: Int) {
        subscription.add(
            restInterface.getMovieReviews(
                movieId,
                Constants.API_KEY,
                SharedPrefs.getLanguageCode(),
                1
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        reviews.value = it.results
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }

}