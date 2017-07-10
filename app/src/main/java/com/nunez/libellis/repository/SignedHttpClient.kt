package com.nunez.libellis.repository

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.R
import okhttp3.OkHttpClient
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import javax.inject.Inject

/**
 * Provides a signed okHttp client
 */
class SignedHttpClient
@Inject constructor(val context: Context) {

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

            val stethoInterceptor = StethoInterceptor()
            val client = OkHttpClient.Builder()
                    .addInterceptor(SigningInterceptor(consumer))
                    .addNetworkInterceptor(stethoInterceptor)
                    .build()

            return client
        }

}