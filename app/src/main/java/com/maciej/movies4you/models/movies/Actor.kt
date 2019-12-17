package com.maciej.movies4you.models.movies

import com.google.gson.annotations.SerializedName

class Actor (

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("cast_id")
    val castId: String,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id : Int,

    @SerializedName("character")
    val character: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("order")
    val order: Int,

    @SerializedName("profile_path")
    val profilePath: String
)