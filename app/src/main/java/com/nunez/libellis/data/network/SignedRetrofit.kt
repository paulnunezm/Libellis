package com.nunez.libellis.data.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * Handles the retrofit signing process.
 */

class SignedRetrofit
constructor(val context: Context) {

    val instance: Retrofit
        get() {
            val retrofit = Retrofit.Builder()
                    .baseUrl(GoodreadsService.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(SignedHttpClient(context).instance)
                    .build()
            return retrofit
        }
}