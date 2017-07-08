package com.nunez.libellis.di

import com.nunez.libellis.main.reading.ReadingFragment
import dagger.Component

@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface NetworkComponent {
    fun inject(f: ReadingFragment)
}