package com.maciej.movies4you.pages.appActivity.myLists.myLists

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
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.StandardListType
import com.maciej.movies4you.base.BaseAppFragment
import com.maciej.movies4you.functional.utils.hideKeyboard
import com.maciej.movies4you.models.body.ListBody
import com.maciej.movies4you.models.movies.UserList
import com.maciej.movies4you.pages.appActivity.details.dialogs.ConfirmDialog
import kotlinx.android.synthetic.main.app_fragment_my_lists.*
import kotlinx.android.synthetic.main.item_mylist_static.view.*


class MyListsFragment : BaseAppFragment(), ConfirmDialog.ConfirmDialogResult {


    private val clickListener: ClickListener = this::onListClicked
    private val clearListener: Clearlistener = this::onClearButtonClicked
    private val deleteListner: DeleteListener = this::onDeleteButtonClicked
    private lateinit var viewModel: MyListsViewModel
    private lateinit var listsAdapter: MyListsAdapter

    var action: (() -> Unit)? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MyListsViewModel::class.java)
        return inflater.inflate(R.layout.app_fragment_my_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actions?.topBar()?.setTitle(getString(R.string.label_my_lists))

        setupStaticLists()
        setupObservables()
        setupRecyclerView()
        setupListeners()

    }

    private fun setupStaticLists() {
        frag_mylists_favorite_list.item_list_title.text = getString(R.string.mylists_favorite_title)
        frag_mylists_watched_list.item_list_title.text = getString(R.string.mylists_watchlist_title)
        frag_mylists_rated_list.item_list_title.text = getString(R.string.mylists_rated_title)
        app_frag_new_list_btn.item_list_title.text = getString(R.string.mylists_add_new_list)

        frag_mylists_favorite_list.setOnClickListener {
            val navDirection =
                MyListsFragmentDirections.actionMyListsFragmentToMySingleListFragment(StandardListType.Favorite, 0)
            findNavController().navigate(navDirection)
        }
        frag_mylists_watched_list.setOnClickListener {
            val navDirection =
                MyListsFragmentDirections.actionMyListsFragmentToMySingleListFragment(StandardListType.Watched, 0)
            findNavController().navigate(navDirection)
        }
        frag_mylists_rated_list.setOnClickListener {
            val navDirection =
                MyListsFragmentDirections.actionMyListsFragmentToMySingleListFragment(StandardListType.Rated, 0)
            findNavController().navigate(navDirection)
        }

        app_frag_new_list_btn.setOnClickListener {
            app_frag_new_list_btn.visibility = View.GONE
            add_list_input_container_new.visibility = View.VISIBLE
            app_frag_mylists_no_items.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        listsAdapter = MyListsAdapter(
            clickListener,
            deleteListner,
            clearListener
        )
        app_frag_mylists_adapter.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = listsAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservables() {

        app_frag_mylists_adapter.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(1) && newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    viewModel.fetchNewPage()
                }
            }
        })

        viewModel.observableProgress.observe(this, Observer {
            if (it == true) {
                app_frag_mylists_progress_bar.visibility = View.VISIBLE
            } else {
                app_frag_mylists_progress_bar.visibility = View.GONE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.observableMyLists.observe(this, Observer {
            if (it == null) {
                app_frag_mylists_no_items.visibility = View.VISIBLE
            } else {
                app_frag_mylists_no_items.visibility = View.GONE
                listsAdapter.update(it)
            }
        })
    }

    private fun setupListeners() {

        frag_mylisys_btn_new_list_accept.setOnClickListener {
            if (frag_mylisys_input_new_list_title.text.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, getString(R.string.toast_new_list_empty_title), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.createNewList(
                    ListBody(
                        frag_mylisys_input_new_list_title.text.toString(),
                        frag_mylisys_input_new_list_description.text.toString(),
                        "pl"
                    )
                )
                onContainerClose()
            }
        }

        frag_mylisys_btn_new_list_cancel.setOnClickListener {
            onContainerClose()
        }
    }

    private fun onContainerClose() {
        if (listsAdapter.itemCount == 0) {
            app_frag_mylists_no_items.visibility = View.VISIBLE
        }
        frag_mylisys_input_new_list_title.text?.clear()
        frag_mylisys_input_new_list_description.text?.clear()
        app_frag_new_list_btn.visibility = View.VISIBLE
        add_list_input_container_new.visibility = View.GONE
        view?.hideKeyboard()
    }

    private fun onListClicked(listId: Int) {
        val navDirection =
            MyListsFragmentDirections.actionMyListsFragmentToMySingleListFragment(StandardListType.Custom, listId)
        findNavController().navigate(navDirection)
    }

    private fun onClearButtonClicked(listId: Int) {
        action = {
            viewModel.clearList(listId)
        }
        val dialog = ConfirmDialog.newInstance(
            R.string.clear_list_dialog_title, R.string.clear_list_dialog_desc, R.drawable.edit
        )
        dialog.setTargetFragment(this@MyListsFragment, Constants.DIALOG_CODE)
        dialog.show(requireFragmentManager(), "dialog")
    }

    private fun onDeleteButtonClicked(list: UserList) {
        action = {
            viewModel.deleteList(list)
        }
        val dialog = ConfirmDialog.newInstance(
            R.string.delete_list_dialog_title, R.string.delete_list_dialog_desc, R.drawable.trash
        )
        dialog.setTargetFragment(this@MyListsFragment, Constants.DIALOG_CODE)
        dialog.show(requireFragmentManager(), "dialog")
    }

    override fun onConfirmDialogResult(value: Boolean) {
        if (value) {
            action?.invoke()
        }
    }
}