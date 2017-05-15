package com.nunez.libellis.di

import com.nunez.libellis.LibellisApp
import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(libellisApp: LibellisApp)
}