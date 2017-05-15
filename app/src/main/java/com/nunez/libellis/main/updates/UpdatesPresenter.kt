package com.nunez.libellis.main.updates

import com.nunez.libellis.entities.Update

/**
 * Created by paulnunez on 5/9/17
 */
class UpdatesPresenter(val view: UpdatesContract.View) : UpdatesContract.Presenter {

    private lateinit var interactor: UpdatesContract.Interactor

    override fun setUpdatesInteractor(interactor: UpdatesContract.Interactor) {
        this.interactor = interactor
        this.interactor.requestUpdates()
    }

    override fun sendUpdates(updates: List<Update>) {
        if (updates.isNotEmpty()) {
            view.showUpdates(updates)
        } else {
            view.showNoBooks()
        }
    }

    override fun showError(message: String) {
        if (message.isNotEmpty())
            view.showError(message)
    }
}