package com.maciej.movies4you.pages.appActivity.discover.sorting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.DiscoverSortType
import kotlinx.android.synthetic.main.dialog_discover_sorting.*

class SortingDialog : DialogFragment(){

    companion object {
        fun newInstance(currentSort: DiscoverSortType?) = SortingDialog().apply {
            this.currentSort = currentSort
        }
    }

    private var currentSort: DiscoverSortType? = null
    private lateinit var sortingAdapter: SortingAdapter
    private var listener: SortingDialogResult? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_discover_sorting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListeners()
    }

    private fun setupAdapter(){
        sortingAdapter = SortingAdapter()
        sortingAdapter.currentCheckedItem = currentSort
        dial_sorting_adapter.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = sortingAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners(){
        dial_sorting_accept_button.setOnClickListener {
            listener?.onSortingDialogResult(sortingAdapter.currentCheckedItem)
            dismiss()
        }

        dial_sorting_close_button.setOnClickListener {
            dismiss()
        }
    }

    fun setClickListener(listener: SortingDialogResult?) {
        this.listener = listener
    }

    interface SortingDialogResult {
        fun onSortingDialogResult(discoverSortType: DiscoverSortType?)
    }
}