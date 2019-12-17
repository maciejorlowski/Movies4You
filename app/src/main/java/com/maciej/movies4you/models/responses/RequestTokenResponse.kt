package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName

class RequestTokenResponse(

    @SerializedName("success")
    val status: Boolean?,

    @SerializedName("expires_at")
    val dueDate: String?,

    @SerializedName("request_token")
    val token: String?
)