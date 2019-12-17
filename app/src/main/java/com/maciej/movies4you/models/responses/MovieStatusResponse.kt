package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName

class MovieStatusResponse(

    @SerializedName("id")
    var id: Int,

    @SerializedName("favorite")
    var favorite: Boolean,

    @SerializedName("rated")
    var rated: Any,

    @SerializedName("watchlist")
    var watchlist: Boolean
)