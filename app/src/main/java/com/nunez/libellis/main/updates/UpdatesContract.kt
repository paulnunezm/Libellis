package com.nunez.libellis.main.updates

import com.nunez.libellis.entities.Shelve
import com.nunez.libellis.entities.Update
import io.reactivex.Observable

interface UpdatesContract {

    interface View {
        fun showUpdates(updates: List<Update>)
        fun showFakeUpdates()
        fun showNoBooks()
        fun showMessage(message: String, error:Boolean = false)
        fun showShelves(shelves: List<Shelve>)
    }

    interface Presenter {
        fun requestUpdates()
        fun sendUpdates(updates: List<Update>)
        fun showError(message: String)
        fun getShelves()
        fun addToShelve(shelve: String, bookId: String)
    }

    interface Interactor {
        fun requestUpdates(): Observable<List<Update>>
        fun requestUserShelves(): Observable<List<Shelve>>
        fun addToShelve(shelve: String, bookId: String) : Observable<Unit>
    }
}