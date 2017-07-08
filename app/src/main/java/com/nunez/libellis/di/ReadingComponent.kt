package com.nunez.libellis.di

import com.nunez.libellis.main.currentlyReading.CurrentlyReadingFragment
import dagger.Component

@Component(modules = arrayOf(AppModule::class, ReadingModule::class))
interface ReadingComponent {
    fun inject(f: CurrentlyReadingFragment)
}