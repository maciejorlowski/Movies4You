package com.maciej.movies4you.pages.appActivity.discover.sorting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.DiscoverSortType


class SortingAdapter : RecyclerView.Adapter<SortingAdapter.ViewHolder>() {

    private var listItems = DiscoverSortType.values()
    private var currentChecked: RadioButton? = null
    var currentCheckedItem: DiscoverSortType? = null

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
            if (currentChecked != holder.item) {
                currentChecked?.isChecked = false
                currentChecked = holder.item
                currentCheckedItem = item
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item: RadioButton = view.findViewById(R.id.item_discover_sort_radiobutton)
    }
}