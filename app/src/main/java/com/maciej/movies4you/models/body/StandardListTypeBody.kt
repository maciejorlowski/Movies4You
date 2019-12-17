package com.maciej.movies4you.models.body

import com.google.gson.annotations.SerializedName

class StandardListTypeBody(

    @SerializedName("media_type")
    var mediaType: String,

    @SerializedName("media_id")
    var mediaId: Int,

    @SerializedName("favorite")
    var favorite: Boolean? = null,

    @SerializedName("watchlist")
    var watchlist: Boolean? = null
)