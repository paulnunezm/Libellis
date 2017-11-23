package com.nunez.libellis.main.reading

import com.nunez.libellis.entities.CurrentlyReadingBook
import io.reactivex.Completable
import io.reactivex.Flowable

interface ReadingContract {

    interface View {
        fun showBooks(books: List<CurrentlyReadingBook>)
        fun showLoading()
        fun hideLoading()
        fun showMessage(message: String, error: Boolean = false)
        fun showNoBooksMessage()
        fun showErrorMessage()
        fun showRefreshing()
        fun hideRefreshing()
    }

    interface Presenter {
        fun getBooks()
        fun sendReadingBooks(readingBooks: List<CurrentlyReadingBook>)
    }

    interface Interactor {
        fun requestBooksObservable(): Flowable<List<CurrentlyReadingBook>>
        fun fetchBooks(): Completable
    }

}