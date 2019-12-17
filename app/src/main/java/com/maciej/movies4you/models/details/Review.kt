package com.maciej.movies4you.models.details

import com.google.gson.annotations.SerializedName

class Review(

    @SerializedName("id")
    val id: String,

    @SerializedName("author")
    val author: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("url")
    val url: String
)