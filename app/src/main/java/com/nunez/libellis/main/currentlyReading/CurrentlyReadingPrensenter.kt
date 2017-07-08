package com.nunez.libellis.main.currentlyReading

import com.nunez.libellis.entities.Update
import javax.inject.Inject

class CurrentlyReadingPrensenter
@Inject constructor(
        val view: CurrentlyReadingContract.View,
        val interactor: CurrentlyReadingContract.Interactor
) : CurrentlyReadingContract.Presenter {

    override fun getBooks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendUpdates(updates: List<Update>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}