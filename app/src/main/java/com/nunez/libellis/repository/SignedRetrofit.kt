package com.nunez.libellis.repository

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Inject

/**
 * Handles the retrofit signing process.
 */

class SignedRetrofit
@Inject constructor(val context: Context) {

    val instance: Retrofit
        get() {

            val retrofit = Retrofit.Builder()
                    .baseUrl(GoodreadsService.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(SignedHttpClient(context).instance)
                    .build()

            return retrofit
        }
}