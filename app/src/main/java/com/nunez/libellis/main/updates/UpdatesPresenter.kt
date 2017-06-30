package com.nunez.libellis.main.updates

import com.nunez.libellis.entities.Update
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdatesPresenter
@Inject constructor(val view: UpdatesContract.View, val interactor: UpdatesContract.Interactor) : UpdatesContract.Presenter {

    override fun requestUpdates() {
        interactor.requestUpdates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { updates ->
                            sendUpdates(updates)
                        },
                        {
                            showError(message = "An error just happended")
                        })
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
            view.showMessage(message, true)
    }

    override fun getShelves() {
        interactor.requestUserShelves().subscribe({
            shelves ->
            view.showShelves(shelves)
        })
    }

    override fun addToShelve(shelve: String, bookId: String) {
        view.showMessage("Adding...")

        interactor.addToShelve(shelve, bookId).subscribe({
            // show successful message
            view.showMessage("Book added")
        }, {
            // show unsuccessful message
            view.showMessage("Book couldn't be added :/")
        })
    }

}