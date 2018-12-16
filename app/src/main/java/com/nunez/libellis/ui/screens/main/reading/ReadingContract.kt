package com.nunez.libellis.ui.screens.main.reading

import com.nunez.libellis.domain.entities.CurrentlyReadingBook
import io.reactivex.Completable
import io.reactivex.Flowable

interface ReadingContract {

    interface View {
        fun showBooks(books: List<CurrentlyReadingBook>)
        fun animateBooks()
        fun showLoading()
        fun hideLoading()
        fun showMessage(message: String, error: Boolean = false)
        fun showNoBooksMessage()
        fun hideNoBooksMessage()
        fun showErrorMessage()
        fun showRefreshing()
        fun hideRefreshing()
        fun showUpdateProgress(id: String, title: String, author: String)
    }

    interface Presenter {
        fun getBooks()
        fun sendReadingBooks(readingBooks: List<CurrentlyReadingBook>)
        fun onBookClicked(id: String, title: String, author: String)
    }

    interface Interactor {
        fun requestBooksObservable(): Flowable<List<CurrentlyReadingBook>>
        fun fetchBooks(): Completable
    }

}