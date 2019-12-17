package com.maciej.movies4you.pages.appActivity.movieDetails.addMovieToList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inverce.mod.v2.core.resources
import com.maciej.movies4you.R
import com.maciej.movies4you.models.movies.UserList
import com.maciej.movies4you.pages.appActivity.home.ClickListener
import kotlinx.android.synthetic.main.item_add_to_list.view.*

typealias ListClickedListener = (Int) -> Unit

class AddMovieToListAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<AddMovieToListAdapter.ViewHolder>() {

    var listItems: List<UserList> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_to_list, parent, false) as View
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]

        holder.title.text = item.listName
        holder.moviesCount.text =
            resources.getQuantityString(R.plurals.movies_count, item.itemCount, item.itemCount)
        holder.description.text = item.description

        holder.moreButton.apply {
            visibility = if (holder.description.text.isNullOrEmpty()) View.GONE else View.VISIBLE

            setOnClickListener {

                it.rotation = if (it.rotation == -90F) 90F else -90F
                holder.description.visibility =
                    if (holder.description.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
        }

        holder.itemView.item_mylist_add_to_list.setOnClickListener {
            clickListener(item.id)
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
        var moreButton: ImageView = view.findViewById(R.id.item_mylist_show_more_btn)
    }
}