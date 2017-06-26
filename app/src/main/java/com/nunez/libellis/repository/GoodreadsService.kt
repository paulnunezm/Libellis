package com.nunez.libellis.repository

import com.nunez.libellis.entities.GoodreadsResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GoodreadsService {
    companion object{
        const val BASE_URL = "https://www.goodreads.com"
        const val REQUEST_TOKEN_URL = BASE_URL + "/oauth/request_token"
        const val ACCESS_TOKEN_URL  = BASE_URL + "/oauth/access_token"
        const val AUTHORIZE_URL     = BASE_URL + "/oauth/authorize?mobile=1"
        const val CALLBACK_URL      = "app://libellis"

        // endpoints for non retrofit calls
        const val UPDATES_ENDPOINT = "/updates/friends.xml"
        const val USER_ID_ENDPOINT = "/api/auth_user"  // Unsighed
    }

    @GET("api/auth_user")
    fun getUserId():Observable<GoodreadsResponse> // Signed request

    @GET("shelf/list.xml")
    fun getShelves(
            @Query("user_id") userId:String,
            @Query("page") page: Int,
            @Query("key") apiKey: String
    ): Call<GoodreadsResponse> // Unsigned request

    @POST("shelf/add_to_shelf.xml")
    fun addToShelve(
            @Path("name") shelveName: String,
            @Path("book_id") bookId: String,

            // Leave this blank unless you're removing from a shelf.
            // If removing, set this to 'remove'. (optional)
            @Path("a") removeTag: String = ""
    ) // Signed request
}