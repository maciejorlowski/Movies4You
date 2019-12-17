package com.maciej.movies4you.pages.appActivity.movieDetails.detailsReviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.R
import com.maciej.movies4you.models.details.Review

class MovieDetailsReviewsAdapter : RecyclerView.Adapter<MovieDetailsReviewsAdapter.ReviewsViewHolder>() {

    var listItems: List<Review> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_details_review, parent, false) as View
        return ReviewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val item = listItems[position]
        holder.author.text = item.author
        holder.content.text = item.content
    }


    fun update(reviews: List<Review>) {
        listItems = reviews
        notifyDataSetChanged()
    }

    inner class ReviewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var author: TextView = view.findViewById(R.id.item_movie_details_review_author)
        var content: TextView = view.findViewById(R.id.item_movie_details_review_content)
    }
}