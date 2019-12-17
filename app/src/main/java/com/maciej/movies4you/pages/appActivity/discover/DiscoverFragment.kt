package com.maciej.movies4you.pages.appActivity.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppFragment
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import com.maciej.movies4you.models.custom.DiscoverQueryData
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.app_fragment_discover.*

class DiscoverFragment : BaseAppFragment() {

    private lateinit var viewModel: DiscoverViewModel
    private lateinit var discoverAdapter: DiscoverAdapter

    private val onMovieClickListener: MovieClickListener = this::onMovieClicked
    private val onAddClickListener: AddClickListener = this::onAddClicked
    private var discoverQueryData = DiscoverQueryData()
    var pageNr = 1

    private var rxEventListener: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (arguments?.get("queryData") as DiscoverQueryData?).takeIf { it != null }.apply {
            discoverQueryData = this ?: DiscoverQueryData()
        }
        (arguments?.getInt("pageNr")).takeIf { it != null }.apply {
            pageNr = this ?: 1
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(DiscoverViewModel::class.java)
        fetchData()
        return inflater.inflate(R.layout.app_fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareTopBar()
        setupAdapter()
        setupAdapterObservables()
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
        rxEventListener?.dispose()
    }

    private fun fetchData() {
        viewModel.loadMovies(pageNr, discoverQueryData)
    }

    private fun prepareTopBar() {

    }

    private fun setupAdapter() {
        discoverAdapter = DiscoverAdapter(onMovieClickListener, onAddClickListener)

        app_frag_discover_adapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = discoverAdapter
        }
    }

    private fun setupAdapterObservables() {
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

        viewModel.observableMovies.observe(this, Observer {
            discoverAdapter.update(it)
            app_frag_discover_next_site_btn.visibility = if (viewModel.nextPageExists) View.VISIBLE else View.GONE
        })
    }

    private fun setupListeners() {
        rxEventListener = RxBus.listen(RxEvent.EventDiscoverMovies::class.java).subscribe {
            discoverQueryData = it.discoverQueryData
            pageNr = 1
            fetchData()
        }

        app_frag_discover_next_site_btn.setOnClickListener {
            val navDirection = DiscoverFragmentDirections.actionDiscoverFragmentSelf(
                pageNr + 1,
                discoverQueryData
            )
            findNavController().navigate(navDirection)
        }
    }

    private fun onMovieClicked(movieId: Int) {
        val navDirection =
            DiscoverFragmentDirections.actionDiscoverFragmentToMovieDetailsFragment(movieId)
        findNavController().navigate(navDirection)
    }

    private fun onAddClicked(movieId: Int) {
    }
}