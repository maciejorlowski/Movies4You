package com.maciej.movies4you.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "userDetails")
class UserDetails(

    @PrimaryKey
    @SerializedName("id")
    val userId: Int,

    @SerializedName("iso_639_1")
    val iso639: String,

    @SerializedName("iso_3166_1")
    val iso3166: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("include_adult")
    val includeAdult: Boolean,

    @SerializedName("username")
    val username: String

)