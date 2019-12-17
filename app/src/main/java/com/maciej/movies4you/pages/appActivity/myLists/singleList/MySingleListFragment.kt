package com.maciej.movies4you.pages.appActivity.myLists.singleList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.StandardListType
import com.maciej.movies4you.base.BaseAppFragment
import com.maciej.movies4you.models.movies.Movie
import com.maciej.movies4you.pages.appActivity.details.dialogs.ConfirmDialog
import kotlinx.android.synthetic.main.app_fragment_single_list.*

class MySingleListFragment : BaseAppFragment(), ConfirmDialog.ConfirmDialogResult {

    private lateinit var viewModel: MySingleListViewModel
    private val clickListener: ClickListener = this::onMovieClicked
    private val deleteListener: DeleteListener = this::onDeleteFromList
    private lateinit var singleListAdapter: MySingleListAdapter
    private val args: MySingleListFragmentArgs by navArgs()

    var action: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MySingleListViewModel::class.java)
        viewModel.initialize(
            args.listType,
            args.listId
        )
        return inflater.inflate(R.layout.app_fragment_single_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actions?.topBar()?.setTitle(getString(args.listType.fragTitle))

        setObservables()
        setupRecyclerView()
    }

    private fun setObservables() {

        app_frag_sigle_list_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, _, _, _ ->
            if(!v.canScrollVertically(1)){
                viewModel.fetchNewPage()
            }
        })

        viewModel.observableProgress.observe(this, Observer {
            if (it == true) {
                app_frag_sigle_list_progress_bar.visibility = View.VISIBLE
            } else {
                app_frag_sigle_list_progress_bar.visibility = View.GONE
            }
        })
        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.observableMoviesList.observe(this, Observer {
            singleListAdapter.update(it)
        })

        viewModel.observableListName.observe(this, Observer {
            actions?.topBar()?.setTitle(it)
        })
    }

    private fun setupRecyclerView() {
        singleListAdapter = MySingleListAdapter(clickListener, deleteListener)

        app_frag_sigle_list_movies_adapter.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = singleListAdapter
            setHasFixedSize(true)
        }
    }


    private fun onMovieClicked(movie: Movie) {
        val navDirection = MySingleListFragmentDirections.actionMySingleListFragmentToMovieDetailsFragment(movie.id)
        findNavController().navigate(navDirection)
    }

    private fun onDeleteFromList(movie: Movie) {
        action = {
            viewModel.deleteMovieFromList(movie)
        }

        val dialog =
            ConfirmDialog.newInstance(
                R.string.delete_movie_from_list_title,
                R.string.delete_movie_from_list_description,
                R.drawable.trash
            )
        dialog.setTargetFragment(this@MySingleListFragment, Constants.DIALOG_CODE)
        dialog.show(requireFragmentManager(), "dialog")
    }

    override fun onConfirmDialogResult(value: Boolean) {
        action?.invoke()
    }
}