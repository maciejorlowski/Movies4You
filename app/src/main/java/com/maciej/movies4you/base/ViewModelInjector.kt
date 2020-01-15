package com.maciej.movies4you.base

import dagger.Component
import com.maciej.movies4you.functional.rest.Rest
import com.maciej.movies4you.pages.appActivity.search.SearchViewModel
import com.maciej.movies4you.pages.appActivity.home.HomeViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.MovieDetailsViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.addMovieToList.AddMovieToListViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsActors.MovieDetailsActorsViewModel
import com.maciej.movies4you.pages.appActivity.movieDetails.detailsReviews.MovieDetailsReviewsViewModel
import com.maciej.movies4you.pages.entryActivity.login.LoginViewModel
import com.maciej.movies4you.pages.appActivity.myLists.myLists.MyListsViewModel
import com.maciej.movies4you.pages.appActivity.myLists.singleList.MySingleListViewModel
import com.maciej.movies4you.pages.appActivity.search.filter.FilterViewModel
import com.maciej.movies4you.pages.appActivity.search.suggestions.SearchSuggestionViewModel
import com.maciej.movies4you.pages.appActivity.tv_shows.popular.PopularTvShowsViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(Rest::class)])
interface ViewModelInjector {

    fun injectLogin(loginViewModel: LoginViewModel)
    fun injectHome(homeViewModel: HomeViewModel)
    fun injectMovieDetails(movieDetailsViewModel: MovieDetailsViewModel)
    fun injectMyLists(myListsViewModel: MyListsViewModel)
    fun injectMySingleList(mysingleList: MySingleListViewModel)
    fun injectDiscover(discoverViewModel: SearchViewModel)
    fun injectMovieDetailsActors(movieDetailsActorsViewModel: MovieDetailsActorsViewModel)
    fun injectMovieDetailsReviews(movieDetailsReviewsViewModel: MovieDetailsReviewsViewModel)
    fun injectAddMovieToList(addMovieToListViewModel: AddMovieToListViewModel)
    fun injectFilter(filterViewModel: FilterViewModel)
    fun injectSearchSuggestion(searchSuggestionViewModel: SearchSuggestionViewModel)
    fun injectPopularTvShows(popularTvShowsViewModel: PopularTvShowsViewModel)


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: Rest): Builder
    }
}