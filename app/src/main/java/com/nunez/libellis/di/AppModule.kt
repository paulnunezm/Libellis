package com.nunez.libellis.di

import android.content.Context
import com.nunez.libellis.LibellisApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: LibellisApp) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return app.applicationContext
    }
}