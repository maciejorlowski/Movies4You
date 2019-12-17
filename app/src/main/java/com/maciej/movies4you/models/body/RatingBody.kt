package com.maciej.movies4you.models.body

import com.google.gson.annotations.SerializedName

class RatingBody(

    @SerializedName("value")
    var rate: Float
)