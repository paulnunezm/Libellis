package com.nunez.libellis.di

import com.nunez.libellis.main.updates.UpdatesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        //        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(AppModule::class, UpdatesModule::class)
)
interface UpdatesComponent {

    fun inject(updatesFragment: UpdatesFragment)
}