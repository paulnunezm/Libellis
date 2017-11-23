package com.nunez.libellis.repository

import android.content.Context
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.R
import okhttp3.OkHttpClient
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

/**
 * Provides a signed okHttp client
 */
class SignedHttpClient
constructor(val context: Context) {

    val instance: OkHttpClient
        get() {
            val prefs = context.getSharedPreferences(context.getString(R.string.user_prefs), 0)
            val userKey = prefs.getString(context.getString(R.string.user_prefs_user_key), "")
            val userSecret = prefs.getString(context.getString(R.string.user_prefs_user_secret), "")

            if(userKey.isEmpty()) throw IllegalArgumentException("user key is not present")
            if(userSecret.isEmpty()) throw IllegalArgumentException("user secret is not present")

            val consumer = OkHttpOAuthConsumer(BuildConfig.GOODREADS_API_KEY,
                    BuildConfig.GOODREADS_API_SECRET)

            consumer.setTokenWithSecret(userKey, userSecret)

            val client = OkHttpClient.Builder()
                    .addInterceptor(SigningInterceptor(consumer))
                    .build()

            return client
        }

}