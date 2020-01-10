package com.maciej.movies4you.models.movies

import com.google.gson.annotations.SerializedName

class Keyword(

    @SerializedName("name")
    var name: String,

    @SerializedName("id")
    var id: Int
)