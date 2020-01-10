package com.maciej.movies4you.pages.appActivity.search.suggestions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog
import com.maciej.movies4you.functional.applyArguments
import kotlinx.android.synthetic.main.dialog_search_suggestion.*

class SearchSuggestionDialog : BaseAppDialog() {

    lateinit var keywordsAdapter: SearchSuggestionAdapter

    companion object {
        const val KEYWORD = "KEYWORD"

        fun newInstance(keyword: String) =
            SearchSuggestionDialog().applyArguments {
                putString(KEYWORD, keyword)
            }.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_search_suggestion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListeners()
//        prepareViews()
//        setOnClickListeners()
    }

    private fun setupAdapter(){
        keywordsAdapter = SearchSuggestionAdapter()
        dial_search_suggestion_adapter.apply {
            adapter = keywordsAdapter
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
        }
    }

    private fun setupListeners(){

    }
}