package com.maciej.movies4you.functional.rest

import com.maciej.movies4you.functional.data.Constants
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.models.body.*
import com.maciej.movies4you.models.db.UserDetails
import com.maciej.movies4you.models.movies.Category
import com.maciej.movies4you.models.movies.MovieDetails
import com.maciej.movies4you.models.responses.*
import io.reactivex.Observable
import retrofit2.http.*


interface RestInterface {

    //--------------------------------ACCOUNT---------------------------------------------------------

    @GET("authentication/token/new")
    fun createRequestToken(@Query("api_key") apiKey: String = Constants.API_KEY): Observable<RequestTokenResponse>

    @POST("authentication/token/validate_with_login")
    fun authorizeRequestToken(@Query("api_key") apiKey: String = Constants.API_KEY, @Body authTokenBody: AuthTokenBody): Observable<RequestTokenResponse>

    @POST("authentication/session/new")
    fun createSession(@Query("api_key") apiKey: String = Constants.API_KEY, @Body sessionBody: SessionBody): Observable<SessionResponse>

    @GET("authentication/guest_session/new")
    fun loginAsGuest(@Query("api_key") apiKey: String = Constants.API_KEY): Observable<GuestSessionResponse>

    @GET("account")
    fun getUserDetails(@Query("api_key") apiKey: String = Constants.API_KEY, @Query("session_id") sessionId: String = SharedPrefs.getSessionId()): Observable<UserDetails>


    //---------------------------------MOVIES------------------------------------------------------

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY, @Query("language") language: String = SharedPrefs.getLanguageCode(), @Query(
            "page"
        ) page: Int
    ): Observable<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = Constants.API_KEY, @Query(
            "language"
        ) language: String = SharedPrefs.getLanguageCode()
    ): Observable<MovieDetails>

    @GET("movie/{movie_id}/account_states")
    fun getMovieStatus(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = Constants.API_KEY, @Query(
            "session_id"
        ) sessionId: String = SharedPrefs.getSessionId()
    ): Observable<MovieStatusResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Observable<MovieCreditsResponse>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String, @Query("language") language: String, @Query(
            "page"
        ) page: Int
    ): Observable<MovieReviewsResponse>

    @POST("movie/{movie_id}/rating")
    fun rateMovie(
        @Path("movie_id") movieId: Int,
        @Body ratingBody: RatingBody,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("guest_session_id") guestSessionId: String = SharedPrefs.getGuestSessionId(),
        @Query("session_id") sessionId: String = SharedPrefs.getSessionId()
    ): Observable<MoviesResponse>

    @GET("discover/{type}")
    fun discoverMovies(
        @Path("type") type: String,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = SharedPrefs.getLanguageCode(),
        @QueryMap options: Map<String, String>
    ): Observable<MoviesResponse>

    @GET("genre/movie/list")
    fun getMovieCategories(@Query("api_key") apiKey: String = Constants.API_KEY, @Query("language") language: String = SharedPrefs.getLanguageCode()): Observable<CategoriesResponse>

    @GET("search/keyword")
    fun getSearchKeywords(@Query("api_key") apiKey: String = Constants.API_KEY, @Query("query") prefix: String, @Query("page") page: Int) : Observable<KeywordsResponse>
    //----------------------------------LISTS---------------------------------------------------------

    @GET("account/{account_id}/lists")
    fun getUserLists(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): Observable<UserListsResponse>

    @POST("list")
    fun createNewList(@Query("api_key") apiKey: String, @Query("session_id") sessionId: String, @Body listBody: ListBody): Observable<CreatelistResponse>

    @DELETE("list/{list_id}")
    fun deleteList(
        @Path("list_id") listId: String, @Query("api_key") apiKey: String = Constants.API_KEY, @Query(
            "session_id"
        ) sessionId: String = SharedPrefs.getSessionId()
    ): Observable<SimpleResponse>

    @POST("list/{list_id}/clear")
    fun clearList(
        @Path("list_id") listId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Query("confirm") confirm: Boolean
    ): Observable<SimpleResponse>

    @POST("account/{account_id}/{list_type}")
    fun modifyStandardList(
        @Path("list_type") listType: String, @Query("api_key") apiKey: String, @Query(
            "session_id"
        ) sessionId: String, @Body body: StandardListTypeBody
    ): Observable<SimpleResponse>

    @GET("list/{list_id}")
    fun getCustomList(@Path("list_id") listId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Observable<CustomListDetailsResponse>

    @GET("account/{account_id}/{list_type}/movies")
    fun getStandardList(
        @Path("list_type") listType: String, @Query("api_key") apiKey: String, @Query("session_id") sessionId: String, @Query(
            "language"
        ) language: String, @Query("page") pageNr: Int
    ): Observable<MoviesResponse>

    @POST("list/{list_id}/add_item")
    fun addMovieToList(
        @Path("list_id") listId: Int,
        @Body contentBody: ManageListContentBody,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("session_id") sessionId: String = SharedPrefs.getSessionId()
    ): Observable<SimpleResponse>

    @POST("list/{list_id}/remove_item")
    fun removeMovieFromList(
        @Path("list_id") listId: Int,
        @Body contentBody: ManageListContentBody,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("session_id") sessionId: String = SharedPrefs.getSessionId()
    ): Observable<SimpleResponse>

    //----------------------------------------------------TV SHOWS------------------------------------------------------------------------------------------
    @GET("tv/{tv_id}")
    fun getTVShowDetails(@Path("tv_id") tvId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Observable<MovieDetails>

    @GET("tv/{tv_id}/account_states")
    fun getTVShowStatus(@Path("tv_id") movieId: Int, @Query("api_key") apiKey: String, @Query("session_id") sessionId: String): Observable<MovieStatusResponse>


}