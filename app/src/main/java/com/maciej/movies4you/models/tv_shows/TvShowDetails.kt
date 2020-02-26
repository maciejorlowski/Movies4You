package com.maciej.movies4you.models.tv_shows

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.movies.Genre
import java.util.*

class TvShowDetails (

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("id")
    val movieId: Int,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("first_air_date")
    val firstAirDate: Date,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("name")
    val name: String
)