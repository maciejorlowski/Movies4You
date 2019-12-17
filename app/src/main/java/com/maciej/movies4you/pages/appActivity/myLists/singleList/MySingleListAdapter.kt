package com.maciej.movies4you.pages.appActivity.myLists.singleList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.picture.GlideImageLoader
import com.maciej.movies4you.models.movies.Movie

typealias ClickListener = (Movie) -> Unit
typealias DeleteListener = (Movie) -> Unit


class MySingleListAdapter(
    private val clickListener: ClickListener,
    val deleteListener: DeleteListener
) : RecyclerView.Adapter<MySingleListAdapter.ViewHolder>() {

    var listItems: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_single_list, parent, false) as View
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            clickListener(listItems[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        val context = holder.itemView.context

        holder.title.text = item.originalTitle
        holder.description.text = item.overview
        GlideImageLoader.loadImage(holder.image, Constants.Urls.IMAGES_URL + item.posterPath)
        holder.vote.text = context.getString(R.string.home_vote_result_max_10, item.voteAverage)
        holder.popularity.text = item.popularity.toString()
        holder.delete.setOnClickListener {
            deleteListener(item)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun update(movies: MutableList<Movie>) {
        listItems = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.item_single_list_movie_image)
        var title: TextView = view.findViewById(R.id.item_single_list_movie_title)
        var description: TextView = view.findViewById(R.id.item_single_list_movie_description)
        var popularity: TextView = view.findViewById(R.id.item_single_list_movie_popularity)
        var vote: TextView = view.findViewById(R.id.item_single_list_movie_vote)
        var delete: ImageView = view.findViewById(R.id.item_single_list_movie_delete_button)
    }
}