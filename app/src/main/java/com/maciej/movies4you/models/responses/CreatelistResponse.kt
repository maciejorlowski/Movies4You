package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName

class CreatelistResponse(

    @SerializedName("status_message")
    val statusMessage: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("status_code")
    val statusCode: Int,

    @SerializedName("list_id")
    val listId: Int
)