package com.maciej.movies4you.pages.appActivity.movieDetails.detailsInfo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inverce.mod.v2.core.verification.isNotNullOrEmpty
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppFragment
import com.maciej.movies4you.functional.utils.toSimpleString
import com.maciej.movies4you.models.movies.MovieDetails
import kotlinx.android.synthetic.main.app_fragment_movie_details_info.*

class MovieDetailsInfoFragment : BaseAppFragment() {

    private lateinit var movieDetails: MovieDetails

    companion object {
        fun newInstance(movieDetails: MovieDetails) = MovieDetailsInfoFragment().apply {
            this.movieDetails = movieDetails
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.app_fragment_movie_details_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareViews()
    }

    private fun prepareViews() {

        app_frag_movie_details_movie_category.text = movieDetails.genres.joinToString { it.name }
        app_frag_movie_details_movie_stats.text = context?.getString(
            R.string.movie_details_details_stats,
            movieDetails.budget,
            movieDetails.releaseDate.toSimpleString(),
            movieDetails.originalLanguage
        )
        if(movieDetails.overview.isNotNullOrEmpty()){
            app_frag_movie_details_movie_description.text = movieDetails.overview
        } else {
            app_frag_movie_details_movie_description.text = context?.getString(R.string.movie_details_no_description)
            app_frag_movie_details_movie_description.gravity = Gravity.CENTER
        }
    }
}