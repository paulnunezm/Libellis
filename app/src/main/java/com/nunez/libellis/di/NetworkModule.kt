package com.nunez.libellis.di

import android.content.Context
import com.nunez.libellis.repository.SignedHttpClient
import com.nunez.libellis.repository.SignedRetrofit
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule(val context: Context) {

    @Provides
    @Singleton
    fun providesSignedRetrofit(): SignedRetrofit {
        return SignedRetrofit(context)
    }

    @Provides
    @Singleton
    fun providesSignedOkHttp(): SignedHttpClient {
        return SignedHttpClient(context)
    }

}

