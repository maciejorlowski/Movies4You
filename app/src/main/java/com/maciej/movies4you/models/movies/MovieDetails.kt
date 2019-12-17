package com.maciej.movies4you.models.movies

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import java.util.*

class MovieDetails(

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("budget")
    val budget: Int,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("id")
    val movieId: Int,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("production_companies")
    val productionCompanies: List<Any>,

    @SerializedName("production_countries")
    val productionCountries: List<Any>,

    @SerializedName("release_date")
    val releaseDate: Date,

    @SerializedName("title")
    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("name")
    val name: String

)