package com.maciej.movies4you.pages.appActivity.movieDetails.detailsReviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.inverce.mod.v2.core.IMEx
import com.inverce.mod.v2.core.verification.isNotNullOrEmpty
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppFragment
import kotlinx.android.synthetic.main.app_fragment_movie_details_reviews.*

class MovieDetailsReviewsFragment : BaseAppFragment() {

    private lateinit var viewModel: MovieDetailsReviewsViewModel
    private lateinit var reviewsAdapter: MovieDetailsReviewsAdapter

    companion object {
        const val MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: Int) = MovieDetailsReviewsFragment().apply {
            arguments = Bundle().apply {
                putInt(MOVIE_ID, movieId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieDetailsReviewsViewModel::class.java)
        viewModel.loadReviews(arguments?.getInt(MOVIE_ID) ?: 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.app_fragment_movie_details_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObservables()
    }

    private fun setupAdapter() {
        reviewsAdapter = MovieDetailsReviewsAdapter()

        app_frag_movie_details_reviews_adapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewsAdapter
        }
    }

    private fun setupObservables() {

        viewModel.observableProgress.observe(this, Observer {
            if (it == true) {
                app_frag_movie_details_reviews_progress.visibility = View.VISIBLE
            } else {
                app_frag_movie_details_reviews_progress.visibility = View.GONE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.observableReviews.observe(this, Observer {
            if (it.isNotNullOrEmpty()) {
                reviewsAdapter.update(it)
                app_frag_movie_details_reviews_empty_data.visibility = View.GONE
            } else {
                app_frag_movie_details_reviews_empty_data.visibility = View.VISIBLE
            }
        })

    }
}