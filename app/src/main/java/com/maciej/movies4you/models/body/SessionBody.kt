package com.maciej.movies4you.models.body

import com.google.gson.annotations.SerializedName

class SessionBody (

    @SerializedName("request_token")
    val requestToken : String?
)