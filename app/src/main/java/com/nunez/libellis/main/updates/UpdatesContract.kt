package com.nunez.libellis.main.updates

import com.nunez.libellis.entities.Update
import io.reactivex.Observable

interface UpdatesContract {

    interface View {
        fun showUpdates(updates: List<Update>)
        fun showFakeUpdates()
        fun showNoBooks()
        fun showError(message: String)
    }

    interface Presenter {
        fun requestUpdates()
        fun sendUpdates(updates: List<Update>)
        fun showError(message: String)
    }

    interface Interactor {
        fun requestUpdates(): Observable<List<Update>>
    }
}