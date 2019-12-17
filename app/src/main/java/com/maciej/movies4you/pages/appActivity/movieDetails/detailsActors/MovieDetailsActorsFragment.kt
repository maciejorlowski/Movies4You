package com.maciej.movies4you.pages.appActivity.movieDetails.detailsActors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppFragment
import kotlinx.android.synthetic.main.app_fragment_movie_details_actors.*

class MovieDetailsActorsFragment : BaseAppFragment() {

    private lateinit var viewModel: MovieDetailsActorsViewModel
    private lateinit var membersAdapter: MovieDetailsActorsAdapter


    companion object {
        const val MOVIE_ID = "MOVIE_ID"

        fun newInstance(movieId: Int) = MovieDetailsActorsFragment().apply {
            arguments = Bundle().apply {
                putInt(MOVIE_ID, movieId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieDetailsActorsViewModel::class.java)
        viewModel.loadCredits(arguments?.getInt(MOVIE_ID) ?: 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.app_fragment_movie_details_actors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObservables()
    }

    private fun setupAdapter() {
        membersAdapter = MovieDetailsActorsAdapter()

        app_frag_movie_details_actors_adapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = membersAdapter
        }
    }

    private fun setupObservables() {

        viewModel.observableProgress.observe(this, Observer {
            if (it == true) {
                app_frag_movie_details_actors_progress.visibility = View.VISIBLE
            } else {
                app_frag_movie_details_actors_progress.visibility = View.GONE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.observableMembers.observe(this, Observer {
            if (it != null) {
                membersAdapter.update(it)
            }
        })

    }
}