package com.maciej.movies4you.models.tv_shows

import com.google.gson.annotations.SerializedName

class TvShow(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("first_air_date")
    val firstAirDate: String,

    @SerializedName("genre_ids")
    val genreIds: Set<Int>,

    @SerializedName("original_name")
    val originalName: String?,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("vote_average")
    val voteAverage: Double
)