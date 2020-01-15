package com.maciej.movies4you.functional.data

object Constants {

    const val API_KEY = "8b8eff3b44f27068166eadedc57792fe"
    const val DIALOG_CODE = 300

    object Urls {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val REGISTER_URL = "https://www.themoviedb.org/account/signup"
        const val IMAGES_URL = "http://image.tmdb.org/t/p/original/"
    }

    object StatusCodes{
        const val PERMISSION_CODE = 3
        const val INTERNAL_ERROR = 11
    }
}