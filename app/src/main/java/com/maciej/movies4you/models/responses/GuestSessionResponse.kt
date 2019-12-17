package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName

class GuestSessionResponse (

    @SerializedName("seccess")
    var success: Boolean,

    @SerializedName("guest_session_id")
    var sessionId : String,

    @SerializedName("expires_at")
    var duedate: String
)