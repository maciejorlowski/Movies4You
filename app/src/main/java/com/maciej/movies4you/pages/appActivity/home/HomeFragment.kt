package com.maciej.movies4you.pages.appActivity.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppFragment
import kotlinx.android.synthetic.main.app_fragment_home.*
import java.lang.Exception

class HomeFragment : BaseAppFragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private val clickListener: ClickListener = this::onMovieClicked


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.app_fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareTopBar()
        setupAdapter()
        setupObservables()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            actions?.topBar()?.showBackArrow(true)
        } catch (e: Exception) {
        }
    }

    private fun prepareTopBar() {
        actions?.topBar()?.showBackArrow(false)
        actions?.topBar()?.setTitle(getString(R.string.label_home))
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter(clickListener)

        app_frag_home_adapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
    }

    private fun setupObservables() {

        app_frag_home_adapter.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    viewModel.loadNextData()
                }
            }
        })


        viewModel.observableProgress.observe(this, Observer {
            if (it) {
                app_frag_home_progress_bar.visibility = View.VISIBLE
            } else {
                app_frag_home_progress_bar.visibility = View.GONE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.observableMovies.observe(this, Observer {
            homeAdapter.update(it)
        })
    }

    private fun onMovieClicked(movieId: Int) {
        val navDirection = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movieId)
        findNavController().navigate(navDirection)
    }
}