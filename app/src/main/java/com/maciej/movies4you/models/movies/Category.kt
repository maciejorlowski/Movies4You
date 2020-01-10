package com.maciej.movies4you.models.movies

import com.google.gson.annotations.SerializedName

class Category(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String
)