package com.nunez.libellis.di

import android.content.Context
import com.nunez.libellis.main.updates.UpdatesContract
import com.nunez.libellis.main.updates.UpdatesInteractor
import com.nunez.libellis.main.updates.UpdatesPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UpdatesModule(val view: UpdatesContract.View) {
    lateinit var interactor:UpdatesInteractor

    @Provides
    @Singleton
    fun provideView(): UpdatesContract.View {
        return view
    }

    @Provides
    @Singleton
    fun providesInteractor(context: Context): UpdatesInteractor{
        interactor = UpdatesInteractor(context)
        return interactor
    }

    @Provides
    @Singleton
    fun providePresenter(): UpdatesPresenter {
        return UpdatesPresenter(view, interactor)
    }
}