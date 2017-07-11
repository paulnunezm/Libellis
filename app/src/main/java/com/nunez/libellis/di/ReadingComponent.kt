package com.nunez.libellis.di

import com.nunez.libellis.main.reading.ReadingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ReadingModule::class))
interface ReadingComponent {
    fun inject(f: ReadingFragment)
}