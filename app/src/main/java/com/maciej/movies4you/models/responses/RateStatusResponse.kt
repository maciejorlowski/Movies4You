package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName

class RateStatusResponse (

    @SerializedName("value")
    var rateValue: Float
)