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
import javax.inject.Singleton

@Singleton
@Component(modules = [(Rest::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun injectLogin(loginViewModel: LoginViewModel)

    fun injectHome(homeViewModel: HomeViewModel)
    fun injectMovieDetails(movieDetailsViewModel: MovieDetailsViewModel)
    fun injectMyLists(myListsViewModel: MyListsViewModel)
    fun injectMySingleList(mysingleList: MySingleListViewModel)
    fun injectDiscover(discoverViewModel: SearchViewModel)
    fun injectmovieDetailsActors(movieDetailsActorsViewModel: MovieDetailsActorsViewModel)
    fun injectmovieDetailsReviews(movieDetailsReviewsViewModel: MovieDetailsReviewsViewModel)
    fun addMovieToList(addMovieToListViewModel: AddMovieToListViewModel)


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: Rest): Builder
    }
}