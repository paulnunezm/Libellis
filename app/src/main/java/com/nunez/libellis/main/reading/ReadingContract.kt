package com.nunez.libellis.main.reading

import com.nunez.libellis.entities.raw.Review
import io.reactivex.Observable

interface ReadingContract {

    interface View {
        fun showBooks(books: List<Review>)
        fun showLoading()
        fun hideLoading()
        fun showMessage(message: String, error: Boolean = false)
        fun showNoBooksMessage()
        fun showErrorMessage()
    }

    interface Presenter {
        fun getBooks()
        fun sendReadingBooks(readingBooks: List<Review>)
    }

    interface Interactor {
        fun requestBooks(): Observable<List<Review>>
    }

}