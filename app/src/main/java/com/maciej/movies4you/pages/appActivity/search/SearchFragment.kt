package com.maciej.movies4you.pages.appActivity.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inverce.mod.v2.core.IMEx
import com.inverce.mod.v2.core.verification.isNotNullOrEmpty
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppFragment
import com.maciej.movies4you.functional.plusAssign
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import com.maciej.movies4you.functional.viewModel
import com.maciej.movies4you.models.custom.DiscoverQueryData
import com.maciej.movies4you.pages.appActivity.movieDetails.addMovieToList.AddMovieToListDialog
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.app_fragment_discover.*

class SearchFragment : BaseAppFragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var discoverAdapter: DiscoverAdapter

    private val onMovieClickListener: MovieClickListener = this::onMovieClicked
    private val onAddClickListener: AddClickListener = this::onAddClicked
    private var discoverQueryData = DiscoverQueryData()

    private lateinit var rxEventListener: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rxEventListener = CompositeDisposable()
        return inflater.inflate(R.layout.app_fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObservables()
        setupEvents()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        actions?.topBar()?.apply {
            showExtendedView(true, discoverQueryData)
            setTitle(getString(R.string.label_discover_movies))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rxEventListener.dispose()
    }

    private fun setupAdapter() {
        discoverAdapter = DiscoverAdapter(
            onMovieClickListener,
            onAddClickListener
        )

        app_frag_discover_adapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = discoverAdapter
        }
    }

    private fun setupObservables() {
        viewModel.observableMovies.observe(this, Observer {
            if (it.isNotNullOrEmpty()) {
                discoverAdapter.update(it)
            }
        })

        viewModel.observableProgress.observe(this, Observer {
            if (it) {
                app_frag_discover_progress_bar.visibility = View.VISIBLE
            } else {
                app_frag_discover_progress_bar.visibility = View.GONE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupEvents() {
        rxEventListener.plusAssign(RxBus.listen(RxEvent.EventSearchMoviesSort::class.java).subscribe {
            viewModel.changeSearchCriteria(viewModel.searchQueryData.apply {
                sortType = it.sortType
            })
            actions?.topBar()?.updateSearchCriteria(viewModel.searchQueryData)
        })
    }

    private fun setupListeners() {

        app_frag_discover_adapter.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    viewModel.loadNextMovies()
                }
            }
        })
    }

    private fun onMovieClicked(movieId: Int) {
        val navDirection =
            SearchFragmentDirections.actionDiscoverFragmentToMovieDetailsFragment(
                movieId
            )
        findNavController().navigate(navDirection)
    }

    private fun onAddClicked(movieId: Int) {
        AddMovieToListDialog.newInstance(movieId)
    }
}