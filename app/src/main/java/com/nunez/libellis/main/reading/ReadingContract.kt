package com.nunez.libellis.main.reading

import com.nunez.libellis.entities.Review
import com.nunez.libellis.entities.Update
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
        fun sendUpdates(updates: List<Update>)
        fun showError(message: String)
    }

    interface Interactor {
        fun requestBooks(): Observable<List<Review>>
    }

}