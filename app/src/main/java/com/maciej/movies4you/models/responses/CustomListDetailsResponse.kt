package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.movies.Movie

class CustomListDetailsResponse(

    @SerializedName("created_by")
    val createdBy: String,

    @SerializedName("desciption")
    val description: String,

    @SerializedName("favorite_count")
    val favouriteCount: Int,

    @SerializedName("id")
    val id : Int,

    @SerializedName("items")
    val movies: List<Movie>,

    @SerializedName("items_count")
    val itemsCount: Int,

    @SerializedName("iso_639_1")
    val iso639: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String?
)