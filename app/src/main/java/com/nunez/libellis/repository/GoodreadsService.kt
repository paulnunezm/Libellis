package com.nunez.libellis.repository

import com.nunez.libellis.entities.GoodreadsResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface GoodreadsService {
    companion object {
        const val BASE_URL = "https://www.goodreads.com"
        const val REQUEST_TOKEN_URL = BASE_URL + "/oauth/request_token"
        const val ACCESS_TOKEN_URL = BASE_URL + "/oauth/access_token"
        const val AUTHORIZE_URL = BASE_URL + "/oauth/authorize?mobile=1"
        const val CALLBACK_URL = "app://libellis"

        // endpoints for non retrofit calls
        const val UPDATES_ENDPOINT = "/updates/friends.xml"
        const val USER_ID_ENDPOINT = "/api/auth_user"  // Unsighed
        const val ADD_TO_SHELVE_ENDPOINT = "/shelf/add_to_shelf.xml" // Signed
    }

    @GET("api/auth_user")
    fun getUserId(): Observable<GoodreadsResponse> // Signed request

    @GET("shelf/list.xml")
    fun getShelves(
            @Query("user_id") userId: String,
            @Query("page") page: Int,
            @Query("key") apiKey: String
    ): Call<GoodreadsResponse> // Unsigned request

    @FormUrlEncoded
    @POST("shelf/add_to_shelf.xml")
    fun addToShelve(
            @Field("name") shelveName: String,
            @Field("book_id") bookId: String,

            // Leave this blank unless you're removing from a shelf.
            // If removing, set this to 'remove'. (optional)
            @Field("a") removeTag: String = ""
    ): Call<ResponseBody>// Signed request

    @GET("review/list?v=2")
    fun getBooksOnShelve(
            @Query("v") version: String = "2",
            @Query("id") userId: String,
            @Query("shelf") shelfName: String, // This is optional, if not specified returns books for every shelf
            @Query("sort") sort: String = "",
            @Query("search") search: String, // Query text to match against member's books (optional)
            @Query("order") order: String = "a", // or d
            @Query("page") page: String = "1",
            @Query("per_page") perPage: String = "",
            @Query("key") developerKey: String
    )
}