package com.nunez.libellis.repository

/**
 * Created by paulnunez on 4/25/17.
 */
interface GoodreadsService {
    companion object{
        const val BASE_URL = "https://www.goodreads.com"
        const val REQUEST_TOKEN_URL = BASE_URL + "/oauth/request_token"
        const val ACCESS_TOKEN_URL  = BASE_URL + "/oauth/access_token"
        const val AUTHORIZE_URL     = BASE_URL + "/oauth/authorize?mobile=1"
        const val CALLBACK_URL      = "app://libellis"
    }
}