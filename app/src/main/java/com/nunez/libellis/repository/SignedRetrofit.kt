package com.nunez.libellis.repository

import android.content.Context
import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

/**
 * Handles the retrofit signing process.
 */

class SignedRetrofit(val context: Context) {

    val instance: Retrofit
        get() {

            val prefs = context.getSharedPreferences(context.getString(R.string.user_prefs), 0)
            val userKey = prefs.getString(context.getString(R.string.user_prefs_user_key), "")
            val userSecret = prefs.getString(context.getString(R.string.user_prefs_user_secret), "")

            Log.d("SignedRetrofit", "key=$userKey, secret $userSecret")

            if(userKey.isEmpty()) throw IllegalArgumentException("user key is not present")
            if(userSecret.isEmpty()) throw IllegalArgumentException("user secret is not present")

            val consumer = OkHttpOAuthConsumer(BuildConfig.GOODREADS_API_KEY,
                    BuildConfig.GOODREADS_API_SECRET)

            consumer.setTokenWithSecret(userKey, userSecret)// this are the values that needs to be saved

            val stethoInterceptor = StethoInterceptor()
            val client = OkHttpClient.Builder()
                    .addInterceptor(SigningInterceptor(consumer))
                    .addNetworkInterceptor(stethoInterceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(GoodreadsService.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(client)
                    .build()

            return retrofit
        }
}