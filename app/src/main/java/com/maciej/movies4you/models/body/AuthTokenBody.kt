package com.maciej.movies4you.models.body

import com.google.gson.annotations.SerializedName

class AuthTokenBody (

    @SerializedName("username")
    val username : String? = null,

    @SerializedName("password")
    val password : String? = null,

    @SerializedName("request_token")
    val requestToken : String? = null
)