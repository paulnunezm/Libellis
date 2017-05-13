package com.nunez.libellis.main.updates

import com.nunez.libellis.entities.Update

interface UpdatesContract {

    interface View {
        fun showUpdates(updates: List<Update>)
        fun showFakeUpdates()
        fun showNoBooks()
        fun showError(message: String)
    }

    interface Presenter {
        fun setUpdatesInteractor(interactor: Interactor)
        fun sendUpdates(updates: List<Update>)
        fun showError(message: String)
    }

    interface Interactor {
        fun requestUpdates()
        fun setPresenter(presenter: Presenter?)
    }
}