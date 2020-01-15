package com.maciej.movies4you.pages.appActivity.tv_shows.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppFragment
import com.maciej.movies4you.functional.viewModel
import kotlinx.android.synthetic.main.app_fragment_popular_tv_shows.*

class PopularTvShowsFragment : BaseAppFragment() {

    private val viewModel by viewModel<PopularTvShowsViewModel>()
    private lateinit var tvShowsAdapter: PopularTvShowsAdapter
    private val clickListener: PopularTvShowListener = this::onTvShowClicked


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.app_fragment_popular_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actions?.topBar()?.setTitle(getString(R.string.label_popular_tv_shows))
        setupAdapter()
        setupObservables()
    }

    private fun setupAdapter() {
        tvShowsAdapter = PopularTvShowsAdapter(clickListener)

        app_frag_popular_tv_shows_adapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tvShowsAdapter
        }
    }

    private fun setupObservables() {

        app_frag_popular_tv_shows_adapter.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    viewModel.loadNextData()
                }
                if (recyclerView.canScrollVertically(-1)) {
                    app_frag_popular_tv_shows_view_goto_top.visibility = View.VISIBLE
                } else {
                    app_frag_popular_tv_shows_view_goto_top.visibility = View.GONE
                }
            }
        })

        app_frag_popular_tv_shows_view_goto_top.setOnClickListener {
            app_frag_popular_tv_shows_adapter.smoothScrollToPosition(0)
        }


        viewModel.observableProgress.observe(this, Observer {
            if (it) {
                app_frag_popular_tv_shows_progress_bar.visibility = View.VISIBLE
            } else {
                app_frag_popular_tv_shows_progress_bar.visibility = View.GONE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.observableTvShows.observe(this, Observer {
            tvShowsAdapter.update(it)
        })
    }

    private fun onTvShowClicked(movieId: Int) {
        //TODO
//        val navDirection = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movieId)
//        findNavController().navigate(navDirection)
    }
}