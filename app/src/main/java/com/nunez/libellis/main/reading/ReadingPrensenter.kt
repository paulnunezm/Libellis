package com.nunez.libellis.main.reading

import com.nunez.libellis.entities.Update
import javax.inject.Inject

class ReadingPrensenter
@Inject constructor(
        val view: ReadingContract.View,
        val interactor: ReadingContract.Interactor
) : ReadingContract.Presenter {

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