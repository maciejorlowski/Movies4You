package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName

class SimpleResponse (

    @SerializedName("status_code")
    val statusCode: Int,

    @SerializedName("status_message")
    val statusMessage: String
)