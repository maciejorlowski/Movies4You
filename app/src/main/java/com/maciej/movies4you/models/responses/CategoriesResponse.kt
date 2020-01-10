package com.maciej.movies4you.models.responses

import com.google.gson.annotations.SerializedName
import com.maciej.movies4you.models.movies.Category

class CategoriesResponse(

    @SerializedName("genres")
    var categories: List<Category>
)