package com.maciej.movies4you.base

import androidx.lifecycle.ViewModel
import com.maciej.movies4you.functional.rest.Rest
import com.maciej.movies4you.pages.appActivity.search.SearchViewModel
import com.maciej.movies4you.pages.appActivity.home.HomeViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.MovieDetailsViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.addMovieToList.AddMovieToListViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsActors.MovieDetailsActorsViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsReviews.MovieDetailsReviewsViewModel
import com.maciej.movies4you.pages.appActivity.myLists.myLists.MyListsViewModel
import com.maciej.movies4you.pages.appActivity.myLists.singleList.MySingleListViewModel
import com.maciej.movies4you.pages.appActivity.search.filter.FilterViewModel
import com.maciej.movies4you.pages.appActivity.search.suggestions.SearchSuggestionViewModel
import com.maciej.movies4you.pages.entryActivity.login.LoginViewModel

abstract class BaseViewModelInjector : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(Rest)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LoginViewModel -> injector.injectLogin(this)
            is HomeViewModel -> injector.injectHome(this)
            is MovieDetailsViewModel -> injector.injectMovieDetails(this)
            is MyListsViewModel -> injector.injectMyLists(this)
            is MySingleListViewModel -> injector.injectMySingleList(this)
            is SearchViewModel -> injector.injectDiscover(this)
            is MovieDetailsActorsViewModel -> injector.injectMovieDetailsActors(this)
            is MovieDetailsReviewsViewModel -> injector.injectMovieDetailsReviews(this)
            is AddMovieToListViewModel -> injector.injectAddMovieToList(this)
            is FilterViewModel -> injector.injectFilter(this)
            is SearchSuggestionViewModel -> injector.searchSuggestion(this)
        }
    }
}