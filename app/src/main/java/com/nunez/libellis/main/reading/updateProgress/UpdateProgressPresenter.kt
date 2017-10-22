package com.nunez.libellis.main.reading.updateProgress

import com.nunez.libellis.entities.UpdateProgress

class UpdateProgressPresenter(
        val reviewId: String,
        val title: String,
        val author: String,
        val view: UpdateProgressSheetContract.View,
        val interactor: UpdateProgressSheetContract.Interactor
) : UpdateProgressSheetContract.Presenter {

    init {
        if (!checkIfWeHaveAllDataNeeded()) {
            view.dismissSheet()
        } else {
            setBookInfo()
        }
    }

    override fun onCancelClicked() {
        view.dismissSheet()
    }

    override fun onUpdateClicked(update: UpdateProgress) {
        view.showLoading()
        if(update.isFinished){
            sendBookFinished(update)
        }else{
            sendBookUpdate(update)
        }
    }

    override fun setBookInfo() {
        view.setBookTitle(title)
        view.setAuthorName(author)
    }

    private fun checkIfWeHaveAllDataNeeded(): Boolean {
        if (reviewId.isEmpty() || title.isEmpty() || author.isEmpty())
            return false
        return true
    }

    private fun sendBookFinished(update: UpdateProgress){
        interactor.bookFinished(update).subscribe({
            view.dismissSheet()
        },{
            view.showError()
        })
    }

    private fun sendBookUpdate(update: UpdateProgress){
        interactor.updateBookReadingLocation(update).subscribe({
            view.dismissSheet()
        }, {
            it.printStackTrace()
            view.showError()
        })
    }
}