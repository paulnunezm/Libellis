package com.nunez.libellis.main.reading.updateProgress

import android.util.Log
import com.nunez.libellis.entities.ReviewUserStatus
import com.nunez.libellis.entities.UpdateProgress

class UpdateProgressPresenter(
        val reviewId: String,
        val view: UpdateProgressSheetContract.View,
        val interactor: UpdateProgressSheetContract.Interactor

) : UpdateProgressSheetContract.Presenter {

    var totalPages: Int = 0

    init {
        Log.d("presenter", "requested")

        interactor.getBookReadingInfo(reviewId).subscribe({
            review ->

            with(review){
                totalPages = book?.numberOfPages as Int
                Log.d("presenter", "on->with === $totalPages")
                userStatuses.let { setReadingStatus(it as List<ReviewUserStatus>) }
            }

            Log.d("presenter", "after")

        }, {
            view.showError()
        }, {})
    }

    override fun setReadingStatus(statuses: List<ReviewUserStatus>) {

        if (statuses.isNotEmpty()) {
            val status = statuses[0]
            val page = status.page
            val percent = status.percent

        } else {

        }
    }

    override fun onCancelClicked() {
        view.dismissSheet()
    }

    override fun onUpdateClicked(update: UpdateProgress) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCommentClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFinishClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBookInfo(title: String, author: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}