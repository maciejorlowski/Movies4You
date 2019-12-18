package com.maciej.movies4you.pages.appActivity.myLists.singleList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.functional.data.*
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.body.ManageListContentBody
import com.maciej.movies4you.models.body.RatingBody
import com.maciej.movies4you.models.body.StandardListTypeBody
import com.maciej.movies4you.models.movies.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MySingleListViewModel : BaseViewModel() {

    private val moviesList = MutableLiveData<MutableList<Movie>>()
    private val listName = MutableLiveData<String>()
    private lateinit var listType: StandardListType
    private var pageNr = 1
    private var listId = 0


    val observableMoviesList: LiveData<MutableList<Movie>>
        get() = moviesList

    val observableListName: LiveData<String>
        get() = listName

    @Inject
    lateinit var restInterface: RestInterface

    fun initialize(listType: StandardListType, listId: Int) {
        moviesList.value = mutableListOf()
        this.listType = listType
        this.listId = listId
        selectListType()
    }

    fun fetchNewPage() {
        pageNr++
        selectListType()
    }

    private fun selectListType() {
        when (listType) {
            StandardListType.Favorite -> {
                loadStandardList()
            }
            StandardListType.Watched -> {
                loadStandardList()
            }
            StandardListType.Rated -> {
                loadStandardList()
            }
            StandardListType.Custom -> {
                loadCustomList()
            }
        }
    }

    private fun loadStandardList() {
        subscription.add(
            restInterface.getStandardList(
                this.listType.queryName,
                Constants.API_KEY,
                SharedPrefs.getSessionId(),
                SharedPrefs.getLanguageCode(),
                pageNr
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        moviesList.value?.addAll(it.movies.filterNotNull())
                        moviesList.value = moviesList.value
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }

    private fun loadCustomList() {
        subscription.add(
            restInterface.getCustomList(listId, Constants.API_KEY, SharedPrefs.getLanguageCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        listName.value = it.name
                        moviesList.value?.addAll(it.movies)
                        moviesList.value = moviesList.value
                    },
                    { error ->
                        onRequestError(error)
                    }
                ))
    }


    fun deleteMovieFromList(movie: Movie) {
        when (listType) {
            StandardListType.Favorite -> {
                deleteFromStandardRequest(
                    StandardListType.Favorite.queryName,
                    StandardListTypeBody(MediaType.Movie.type, movie.id, favorite = false)
                ) {
                    onRemoveSuccess(movie)
                }
            }
            StandardListType.Watched -> {
                deleteFromStandardRequest(
                    StandardListType.Watched.queryName,
                    StandardListTypeBody(MediaType.Movie.type, movie.id, watchlist = false)
                ) {
                    onRemoveSuccess(movie)
                }
            }
            StandardListType.Rated -> {
                //TODO canot remove movies from this list
//                deleteFromRateListRequest(movie.id) {
//                    onRemoveSuccess(movie)
//                }
            }
            StandardListType.Custom -> {
                deleteFromCustomRequest(listId, ManageListContentBody(movie.id)) {
                    onRemoveSuccess(movie)
                }
            }
        }
    }

    private fun onRemoveSuccess(movie: Movie) {
        moviesList.value?.remove(movie)
        moviesList.value = moviesList.value
    }


    private fun deleteFromStandardRequest(
        listType: String,
        requestBody: StandardListTypeBody,
        done: (Boolean) -> Unit
    ) {
        subscription.add(restInterface.modifyStandardList(
            listType,
            Constants.API_KEY,
            SharedPrefs.getSessionId(),
            requestBody
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    done(true)
                },
                { error ->
                    onRequestError(error)
                    done(false)
                }
            ))
    }

    private fun deleteFromCustomRequest(
        listId: Int,
        requestBody: ManageListContentBody,
        done: (Boolean) -> Unit
    ) {
        subscription.add(restInterface.removeMovieFromList(
            listId,
            requestBody
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    done(true)
                },
                { error ->
                    onRequestError(error)
                    done(false)
                }
            ))
    }

    private fun deleteFromRateListRequest(movieId: Int, done: (Boolean) -> Unit) {
        subscription.add(
            restInterface.rateMovie(
                movieId, RatingBody(0F)
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { onRequestStart() }
                .doOnTerminate { onRequestExecute() }
                .subscribe(
                    {
                        done(true)
                    },
                    { error ->
                        done(false)
                        onRequestError(error)
                    }
                ))
    }
}