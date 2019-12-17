package com.maciej.movies4you.functional.data

import androidx.annotation.StringRes
import com.maciej.movies4you.R

enum class AppLanguage(var langCode: String, var position: Int) {
    PL("pl", 0),
    EN("en", 1),
    DE("de", 2),
    RU("ru", 3);

    constructor() {
        this.langCode = PL.langCode
    }
}

enum class StandardListType(var queryName: String, @StringRes var fragTitle: Int) {
    Favorite("favorite", R.string.label_favorite),
    Watched("watchlist", R.string.label_watchlist),
    Rated("rated", R.string.label_rated),
    Custom("", R.string.label_other)
}

enum class MediaType(var type: String, var position: Int) {
    Movie("movie", 0),
    TVShow("tv", 1)
}

enum class DiscoverSortType(var queryName: String, @StringRes var resNameId: Int) {
    POPULARITY_ASC("popularity.asc", R.string.discover_sort_popularity_asc),
    POPULARITY_DESC("popularity.desc", R.string.discover_sort_popularity_desc),
    RELEASE_DATE_ASC("release_date.asc", R.string.discover_sort_release_date_asc),
    RELEASE_DATE_DESC("release_date.desc", R.string.discover_sort_release_date_desc),
    REVENUE_ASC("revenue.asc", R.string.discover_sort_revenue_asc),
    REVENUE_DESC("revenue.desc", R.string.discover_sort_revenue_desc),
    ORIGINAL_TITLE_ASC("original_title.asc", R.string.discover_sort_original_title_asc),
    ORIGINAL_TITLE_DESC("original_title.desc", R.string.discover_sort_original_title_desc),
    VOTE_AVERAGE_ASC("vote_average.asc", R.string.discover_sort_vote_average_asc),
    VOTE_AVERAGE_DESC("vote_average.desc", R.string.discover_sort_vote_average_desc),
    VOTE_COUNT_ASC("vote_count_asc.asc", R.string.discover_sort_vote_count_asc),
    VOTE_COUNT_DESC("vote_count_asc.desc", R.string.discover_sort_vote_count_desc)
}