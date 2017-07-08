package com.nunez.libellis.di

import android.content.Context
import com.nunez.libellis.main.reading.ReadingContract
import com.nunez.libellis.main.reading.ReadingInteractor
import com.nunez.libellis.main.reading.ReadingPrensenter
import com.nunez.libellis.repository.SignedRetrofit
import dagger.Provides
import javax.inject.Singleton


class ReadingModule(val view: ReadingContract.View) {
    lateinit var interactor: ReadingInteractor

    @Provides
    @Singleton
    fun provideView(): ReadingContract.View {
        return view
    }

    @Provides
    @Singleton
    fun providesInteractor(context: Context, retrofit: SignedRetrofit): ReadingInteractor {
        interactor = ReadingInteractor(context, retrofit)
        return interactor
    }

    @Provides
    @Singleton
    fun providePresenter(): ReadingPrensenter {
        return ReadingPrensenter(view, interactor)
    }
}