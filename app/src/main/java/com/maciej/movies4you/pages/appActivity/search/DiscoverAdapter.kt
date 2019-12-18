package com.maciej.movies4you.pages.appActivity.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.picture.GlideImageLoader
import com.maciej.movies4you.models.movies.Movie

typealias MovieClickListener = (Int) -> Unit
typealias AddClickListener = (Int) -> Unit

class DiscoverAdapter(
    private val movieClickListener: MovieClickListener,
    private val addClickListener: AddClickListener
) : RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder>() {

    var listItems: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DiscoverViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_discover, parent, false) as View
        val viewHolder = DiscoverViewHolder(view)
        view.setOnClickListener {
            movieClickListener(listItems[viewHolder.adapterPosition].id)
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        val item = listItems[position]
        val context = holder.itemView.context
        holder.actionBtn.setOnClickListener(null)

        holder.title.text = item.originalTitle
        holder.description.text = item.overview
        GlideImageLoader.loadImage(holder.image, Constants.Urls.IMAGES_URL + item.posterPath)

        holder.vote.text = context.getString(R.string.home_vote_result_max_10, item.voteAverage)
        holder.popularity.text = item.popularity.toString()

        holder.actionBtn.setOnClickListener {
            addClickListener(item.id)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun update(movies: List<Movie>) {
        listItems = movies
        notifyDataSetChanged()
    }

    inner class DiscoverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.item_discover_image)
        var title: TextView = view.findViewById(R.id.item_discover_title)
        var description: TextView = view.findViewById(R.id.item_discover_description)
        var popularity: TextView = view.findViewById(R.id.item_discover_popularity_txt)
        var vote: TextView = view.findViewById(R.id.item_discover_vote_txt)
        var actionBtn: ImageView = view.findViewById(R.id.item_discover_add_button)
    }
}