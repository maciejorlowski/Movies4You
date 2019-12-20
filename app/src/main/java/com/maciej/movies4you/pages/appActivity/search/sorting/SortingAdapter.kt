package com.maciej.movies4you.pages.appActivity.search.sorting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.SearchSortType

typealias SelectedListener = (SearchSortType) -> Unit

class SortingAdapter(private val selectedListener: SelectedListener) :
    RecyclerView.Adapter<SortingAdapter.ViewHolder>() {

    private var listItems = SearchSortType.values()
    private var currentChecked: RadioButton? = null
    var currentCheckedItem: SearchSortType? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_discover_sort, parent, false) as View
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        val context = holder.itemView.context
        holder.item.setOnClickListener(null)

        if (item == currentCheckedItem) {
            holder.item.isChecked = true
            currentChecked = holder.item
        }
        holder.item.text = context.getString(item.resNameId)
        holder.item.setOnClickListener {
                selectedListener(item)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item: RadioButton = view.findViewById(R.id.item_discover_sort_radiobutton)
    }
}