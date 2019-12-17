package com.maciej.movies4you.models.errorResponses

import com.google.gson.annotations.SerializedName

class APIError (

    @SerializedName("status_message")
    val message: String? = null,

    @SerializedName("status_code")
    val code: Int? = null
)