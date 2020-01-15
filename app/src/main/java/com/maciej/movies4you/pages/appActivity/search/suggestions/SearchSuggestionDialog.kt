package com.maciej.movies4you.pages.appActivity.search.suggestions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog
import com.maciej.movies4you.functional.applyArguments
import com.maciej.movies4you.functional.viewModel
import kotlinx.android.synthetic.main.dialog_search_suggestion.*

class SearchSuggestionDialog : BaseAppDialog() {

    lateinit var keywordsAdapter: SearchSuggestionAdapter
    private val keywordListener: KeywordListener = this::onKeywordSelected

    private val viewModel by viewModel<SearchSuggestionViewModel>()

    companion object {
        const val KEYWORD = "KEYWORD"

        fun newInstance(keyword: String) =
            SearchSuggestionDialog().applyArguments {
                putString(KEYWORD, keyword)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.initialize(arguments?.getString(KEYWORD) ?: "")
        return inflater.inflate(R.layout.dialog_search_suggestion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListeners()
    }

    private fun setupAdapter() {
        keywordsAdapter = SearchSuggestionAdapter(keywordListener)
        dial_search_suggestion_adapter.apply {
            adapter = keywordsAdapter
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
        }
    }

    private fun onKeywordSelected(id: Int) {
        val listener: SuggestionDialogCallback = targetFragment as SuggestionDialogCallback
        listener.onSuggestionDialogCallback(id)
        dismiss()
    }

    private fun setupListeners() {

        dial_search_suggestion_adapter.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    viewModel.loadKeywords()
                }
            }
        })

        viewModel.observableKeywords.observe(this, Observer {
            keywordsAdapter.updateData(it)
        })

        dial_search_suggestion_close.setOnClickListener {
            dismiss()
        }
    }

    interface SuggestionDialogCallback {
        fun onSuggestionDialogCallback(keywordId: Int)
    }
}