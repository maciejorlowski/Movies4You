package com.maciej.movies4you.pages.appActivity.search.suggestions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.R
import com.maciej.movies4you.models.movies.Keyword

typealias KeywordListener = (Int) -> Unit

class SearchSuggestionAdapter(var listener: KeywordListener) : RecyclerView.Adapter<SearchSuggestionAdapter.ViewHolder>(){

    var listItems: List<Keyword> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_suggested_keyword, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]

        holder.view.text = item.name
        holder.view.setOnClickListener {
            listener(item.id)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun updateData(items: List<Keyword>) {
        listItems = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) :  RecyclerView.ViewHolder(view){
        var view = view as RadioButton
    }
}