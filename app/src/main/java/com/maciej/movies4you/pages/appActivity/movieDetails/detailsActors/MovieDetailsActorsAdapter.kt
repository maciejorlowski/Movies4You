package com.maciej.movies4you.pages.appActivity.movieDetails.detailsActors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.picture.GlideImageLoader
import com.maciej.movies4you.models.movies.Actor
import com.maciej.movies4you.models.movies.CrewMember

class MovieDetailsActorsAdapter : RecyclerView.Adapter<MovieDetailsActorsAdapter.AcstorsViewHolder>() {

    var listItems: List<Any> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcstorsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_details_actor, parent, false) as View
        return AcstorsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: AcstorsViewHolder, position: Int) {
        when (listItems[position]) {
            is CrewMember -> bindCrew(holder, listItems[position] as CrewMember)
            is Actor -> bindCast(holder, listItems[position] as Actor)
        }
    }

    private fun bindCrew(holder: AcstorsViewHolder, item: CrewMember) {
        GlideImageLoader.loadCircleImage(
            holder.image, Constants.Urls.IMAGES_URL + item.profilePath
        )
        holder.name.text = item.name
        holder.role.text = item.job
    }

    private fun bindCast(holder: AcstorsViewHolder, item: Actor) {
        GlideImageLoader.loadCircleImage(
            holder.image, Constants.Urls.IMAGES_URL + item.profilePath
        )
        holder.name.text = item.name
        holder.role.text = item.character
    }

    fun update(members: List<Any>) {
        listItems = members
        notifyDataSetChanged()
    }


    inner class AcstorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.item_movie_details_actor_image)
        var name: TextView = view.findViewById(R.id.item_movie_details_actor_name)
        var role: TextView = view.findViewById(R.id.item_movie_details_actor_role)
    }
}