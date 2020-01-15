package com.maciej.movies4you.pages.appActivity.tv_shows.popular

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
import com.maciej.movies4you.models.tv_shows.TvShow

typealias PopularTvShowListener = (Int) -> Unit

class PopularTvShowsAdapter(val clickListener: PopularTvShowListener) :
    RecyclerView.Adapter<PopularTvShowsAdapter.MoviesViewHolder>() {

    var listItems: List<TvShow> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false) as View
        val viewHolder = MoviesViewHolder(view)
        view.setOnClickListener {
            clickListener(listItems[viewHolder.adapterPosition].id)
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onBindViewHolder(viewHolder: MoviesViewHolder, position: Int) {
        val item = listItems[position]
        val context = viewHolder.itemView.context

        viewHolder.title.text = item.name
        viewHolder.description.text = item.overview
        GlideImageLoader.loadImage(viewHolder.image, Constants.Urls.IMAGES_URL + item.posterPath)
        viewHolder.vote.text = context.getString(R.string.home_vote_result_max_10, item.voteAverage)
        viewHolder.popularity.text = item.popularity.toString()
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun update(data: List<TvShow>) {
        listItems = data
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.item_home_movie_image)
        var title: TextView = view.findViewById(R.id.item_home_movie_title)
        var description: TextView = view.findViewById(R.id.item_home_movie_description)
        var popularity: TextView = view.findViewById(R.id.item_home_movie_popularity_txt)
        var vote: TextView = view.findViewById(R.id.item_home_movie_vote_txt)
    }
}