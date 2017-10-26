package com.nunez.libellis.repository

import com.nunez.libellis.BuildConfig
import com.nunez.libellis.entities.GoodreadsResponse
import io.reactivex.Completable
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

    @Deprecated("Use the Rx call instead", ReplaceWith("getBooksOnShelfRX"))
    @GET("review/list?v=2")
    fun getBooksOnShelf(
            @Query("v") version: String = "2",
            @Query("id") userId: String,
            @Query("shelf") shelfName: String, // This is optional, if not specified returns books for every shelf
            @Query("sort") sort: String = "",
            @Query("search") search: String, // Query text to match against member's books (optional)
            @Query("order") order: String = "a", // or d
            @Query("page") page: String = "1",
            @Query("per_page") perPage: String = "",
            @Query("key") developerKey: String
    ): Call<GoodreadsResponse>

    @GET("review/list?v=2")
    fun getBooksOnShelfRX(
            @Query("v") version: String = "2",
            @Query("id") userId: String,
            @Query("shelf") shelfName: String, // This is optional, if not specified returns books for every shelf
            @Query("sort") sort: String = "desc",
            @Query("search") search: String = "", // Query text to match against member's books (optional)
            @Query("order") order: String = "a", // or d
            @Query("page") page: String = "1",
            @Query("per_page") perPage: String = "",
            @Query("key") developerKey: String = BuildConfig.GOODREADS_API_KEY
    ): Observable<GoodreadsResponse>


    /** Gets a user review. This also retrieves the currently reading information about a book.*/
    @GET("review/show.xml")
    fun getReview(
            @Query("key") developerKey: String,
            @Query("id") reviewId: String
    ): Call<GoodreadsResponse>

    @FormUrlEncoded
    @POST("user_status.xml")
    fun sendBookUpdate(
            @Field("user_status[book_id]") id: String,
            @Field("user_status[page]") page: String = "",
            @Field("user_status[percent]") percent: String = "",
            @Field("user_status[body]") comment: String = ""//: status update (required, unless page or percent is present, then it is optional)
    ): Completable // Signed request

    @FormUrlEncoded
    @POST("review.xml")
    fun sendBookFinished(
            @Field("book_id") id: String,
            @Field("review[review]") comment: String = "", //Text of the review (optional)
            @Field("review[rating]") rating: Int = 0, //(0-5) (optional, default is 0 (No rating))
            @Field("shelf") shelf: String = "read" //read|currently-reading|to-read|<USER SHELF NAME> (optional, must exist, see: shelves.list)
            //@Field("review[read_at]") //: Date (YYYY-MM-DD format, e.g. 2008-02-01) (optional)
    ): Completable// Signed request
}