package com.maciej.movies4you.models.body

import com.google.gson.annotations.SerializedName

class ManageListContentBody(

    @SerializedName("media_id")
    var mediaId: Int
)