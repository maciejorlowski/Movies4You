package com.maciej.movies4you.pages.appActivity.myLists.myLists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.inverce.mod.v2.core.resources
import com.maciej.movies4you.R
import com.maciej.movies4you.models.movies.UserList


typealias ClickListener = (Int) -> Unit
typealias DeleteListener = (UserList) -> Unit
typealias Clearlistener = (Int) -> Unit

class MyListsAdapter(
    private val clickListener: ClickListener,
    private val deleteListener: DeleteListener,
    private val clearlistener: Clearlistener
) :RecyclerView.Adapter<MyListsAdapter.ViewHolder>() {

    var listItems: List<UserList> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_list, parent, false) as View
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            clickListener(listItems[viewHolder.adapterPosition].id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]

        holder.title.text = item.listName
        holder.moviesCount.text =
            resources.getQuantityString(R.plurals.movies_count, item.itemCount, item.itemCount)
        holder.description.text = item.description

        holder.deleteButton.setOnClickListener {
            deleteListener(item)
        }

        holder.clearButton.setOnClickListener {
            clearlistener(item.id)
        }

        holder.moreButton.apply {

            visibility = if(holder.description.text.isNullOrEmpty()) View.GONE else View.VISIBLE

            setOnClickListener {
                it.rotation = if(it.rotation == -90F) 90F else -90F
                holder.description.visibility = if(holder.description.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
        }


    }


    override fun getItemCount(): Int {
        return listItems.size
    }

    fun update(movies: List<UserList>) {
        listItems = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_list_title)
        var moviesCount: TextView = view.findViewById(R.id.item_list_amount)
        var description: TextView = view.findViewById(R.id.item_mylist_description)
        var clearButton: ImageView = view.findViewById(R.id.item_list_btn_clear)
        var deleteButton: ImageView = view.findViewById(R.id.item_list_btn_delete)
        var moreButton: ImageView = view.findViewById(R.id.item_mylist_show_more_btn)
    }
}
