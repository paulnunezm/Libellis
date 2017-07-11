package com.nunez.libellis.main.reading

import com.nunez.libellis.entities.Review
import io.reactivex.Observable

interface ReadingContract {

    interface View {
        fun showBooks(books: List<Review>)
        fun showLoading()
        fun showNoBooks()
        fun showMessage(message: String, error: Boolean = false)
    }

    interface Presenter {
        fun getBooks()
        fun sendReadingBooks(readingBooks: List<Review>)
    }

    interface Interactor {
        fun requestBooks(): Observable<List<Review>>
    }

}