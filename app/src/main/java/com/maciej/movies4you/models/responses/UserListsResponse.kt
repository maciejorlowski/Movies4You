package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.movies.UserList

class UserListsResponse (

    @SerializedName("page")
    val page : Int,

    @SerializedName("results")
    val lists: List<UserList>,

    @SerializedName("total_pages")
    val totalPages : Int,

    @SerializedName("total_results")
    val totalResults: Int
)