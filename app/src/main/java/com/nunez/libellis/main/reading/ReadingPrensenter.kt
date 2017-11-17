package com.nunez.libellis.main.reading

import com.nunez.libellis.NoConnectivityException
import com.nunez.libellis.entities.raw.Review
import io.reactivex.android.schedulers.AndroidSchedulers

class ReadingPrensenter(
        val view: ReadingContract.View,
        val interactor: ReadingContract.Interactor
) : ReadingContract.Presenter {

    override fun getBooks() {
        interactor.requestBooks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.hideLoading()
                    if (it.isEmpty())
                        view.showNoBooksMessage()
                    else
                        sendReadingBooks(it)
                }, { error ->
                    view.hideLoading()
                    if (error is NoConnectivityException)
                        view.showErrorMessage()
                    else
                        view.showMessage("Ups! Something seems wrong", true)
                })
    }

    override fun sendReadingBooks(readingBooks: List<Review>) {
        if (readingBooks.isNotEmpty()) {
            view.showBooks(readingBooks)
        } else {
            view.showNoBooksMessage()
        }
    }
}