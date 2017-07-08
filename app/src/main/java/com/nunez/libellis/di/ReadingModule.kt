package com.nunez.libellis.di

import android.content.Context
import com.nunez.libellis.main.currentlyReading.CurrentlyReadingContract
import com.nunez.libellis.main.currentlyReading.CurrentlyReadingInteractor
import com.nunez.libellis.main.currentlyReading.CurrentlyReadingPrensenter
import com.nunez.libellis.repository.SignedRetrofit
import dagger.Provides
import javax.inject.Singleton


class ReadingModule(val view: CurrentlyReadingContract.View) {
    lateinit var interactor: CurrentlyReadingInteractor

    @Provides
    @Singleton
    fun provideView(): CurrentlyReadingContract.View {
        return view
    }

    @Provides
    @Singleton
    fun providesInteractor(context: Context, retrofit: SignedRetrofit): CurrentlyReadingInteractor {
        interactor = CurrentlyReadingInteractor(context, retrofit)
        return interactor
    }

    @Provides
    @Singleton
    fun providePresenter(): CurrentlyReadingPrensenter {
        return CurrentlyReadingPrensenter(view, interactor)
    }
}