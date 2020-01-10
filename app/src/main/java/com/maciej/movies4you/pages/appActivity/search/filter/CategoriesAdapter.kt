package com.maciej.movies4you.pages.appActivity.search.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.R
import com.maciej.movies4you.models.movies.Category

class CategoriesAdapter(var selectedItems: MutableList<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var listItems: List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filter_category, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]

        holder.view.text = item.name
        holder.view.isChecked = selectedItems.map { it.id }.contains(item.id)
        holder.view.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> selectedItems.add(item)
                false -> selectedItems.remove(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun updateData(items: List<Category>) {
        listItems = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var view = view as CheckBox
    }

}