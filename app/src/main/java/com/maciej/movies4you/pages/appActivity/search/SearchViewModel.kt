package com.maciej.movies4you.pages.appActivity.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maciej.movies4you.base.BaseViewModel
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.functional.rest.RestInterface
import com.maciej.movies4you.models.custom.search.DiscoverQueryData
import com.maciej.movies4you.models.movies.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import retrofit2.http.Query
import javax.inject.Inject
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.inverce.mod.v2.core.verification.isNotNullOrEmpty
import com.maciej.movies4you.functional.data.MediaType


class SearchViewModel : BaseViewModel() {

    private var movies = MutableLiveData<MutableList<Movie>>()

    var pageNr: Int = 0

    var searchQueryData: DiscoverQueryData =
        DiscoverQueryData()

    val observableMovies: LiveData<MutableList<Movie>>
        get() = movies

    @Inject
    lateinit var restInterface: RestInterface

    init {
        movies.value = mutableListOf()
        loadNextMovies()
    }

    fun loadNextMovies() {
        val queryData = queryData()
        subscription.add(restInterface.discoverMovies(
            type = searchQueryData.filterData.discoverType.type,
            options = queryData
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onRequestStart() }
            .doOnTerminate { onRequestExecute() }
            .subscribe(
                {
                    movies.value?.addAll(it.movies.filterNotNull())
                    movies.value = movies.value
                },
                { error ->
                    onRequestError(error)
                }
            ))
    }
//    @Query("page") page: Int?,
//    @Query("sort_by") sortBy: String?,
//    @Query("with_keywords") searchPrefix: String?,
//    @Query("include_adult") includeAdult: Boolean?,
//    @Query("release_date.gte") minReleaseDate: String?,
//    @Query("release_date.lte") maxReleaseDate: String?,
//    @Query("vote_count.gte") minVoteCount: Int?,
//    @Query("vote_count.lte") maxVoteCount: Int?,
//    @Query("vote_average_gte") maxVoteAverage: Int?,
//    @Query("vote_average_lte") minVoteAverage: Int?

    private fun queryData(): HashMap<String, String> {
        val data = HashMap<String, String>()
        data["page"] = (++pageNr).toString()
        data["sort_by"] = searchQueryData.sortType.queryName
        if (searchQueryData.filterData.minReleaseYear.isNotNullOrEmpty()) {
            data["release_date.gte"] = searchQueryData.filterData.minReleaseYear ?: ""
        }
        if (searchQueryData.filterData.maxReleaseYear.isNotNullOrEmpty()) {
            data["release_date.lte"] = searchQueryData.filterData.maxReleaseYear ?: ""
        }
        if (searchQueryData.filterData.minVoteCount != null) {
            data["vote_count.gte"] = searchQueryData.filterData.minVoteCount.toString()
        }
        if (searchQueryData.searchPrefix.isNotNullOrEmpty()) {
            data["with_keywords"] = searchQueryData.searchPrefix
        }
        if (searchQueryData.filterData.categories.isNotNullOrEmpty()) {
            data["with_genres"] =
                searchQueryData.filterData.categories?.joinToString(",") { it.id.toString() } ?: ""
        }
        return data
    }

    fun changeSearchCriteria(newQueryData: DiscoverQueryData) {
        this.searchQueryData = newQueryData
        pageNr = 0
        movies.value?.clear()
        loadNextMovies()
    }


}