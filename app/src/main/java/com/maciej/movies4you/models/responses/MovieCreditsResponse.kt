package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.movies.Actor
import com.maciej.movies4you.models.movies.CrewMember

class MovieCreditsResponse(

    @SerializedName("id")
    val movieId: Int,

    @SerializedName("cast")
    val actors : List<Actor>,

    @SerializedName("crew")
    val crew : List<CrewMember>

)