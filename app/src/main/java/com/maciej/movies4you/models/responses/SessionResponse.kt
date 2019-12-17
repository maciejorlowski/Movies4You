package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName

class SessionResponse(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("session_id")
    val sessionId: String

)