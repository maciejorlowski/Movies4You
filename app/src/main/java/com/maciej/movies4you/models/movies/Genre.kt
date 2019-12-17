package com.maciej.movies4you.models.movies

import com.google.gson.annotations.SerializedName

class Genre (

    @SerializedName("id")
    val genreId: Int,


    @SerializedName("name")
    val name: String
)