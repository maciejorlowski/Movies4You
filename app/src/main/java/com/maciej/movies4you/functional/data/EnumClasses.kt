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

enum class SearchSortType(
    var queryValue: String, @StringRes var resNameId: Int,
    var order: SearchOrderType?, var queryName: String = queryValue + order?.queryPrefix
) {

    POPULARITY("popularity", R.string.discover_sort_popularity, SearchOrderType.DESC),
    RELEASE_DATE("release_date", R.string.discover_sort_release_date, SearchOrderType.DESC),
    REVENUE("revenue.desc", R.string.discover_sort_revenue, SearchOrderType.DESC),
    ORIGINAL_TITLE("original_title", R.string.discover_sort_original_title, SearchOrderType.DESC),
    VOTE_AVERAGE("vote_average", R.string.discover_sort_vote_average, SearchOrderType.DESC),
    VOTE_COUNT("vote_count_asc", R.string.discover_sort_vote_count, SearchOrderType.DESC)
}

enum class SearchOrderType(var queryPrefix: String) {
    ASC(".asc"),
    DESC(".desc")
}

enum class FilterPopularityCount(var position: Int, @StringRes var resNameId: Int, var voteCount : Int){
    LOW(0,R.string.popularity_low, 0),
    MEDIUM(1,R.string.popularity_medium, 5000),
    HIGH(2,R.string.popularity_high, 10000)
}