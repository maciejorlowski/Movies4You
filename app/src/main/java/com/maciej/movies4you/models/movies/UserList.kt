package com.maciej.movies4you.models.movies

import com.google.gson.annotations.SerializedName

class UserList(

    @SerializedName("description")
    val description: String,

    @SerializedName("favourite_count")
    val favouriteCount: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("item_count")
    var itemCount: Int,

    @SerializedName("iso_639_1")
    val iso639: String,

    @SerializedName("list_type")
    val listType: String,

    @SerializedName("name")
    val listName: String,

    @SerializedName("poster_path")
    val posterPath: String?
)