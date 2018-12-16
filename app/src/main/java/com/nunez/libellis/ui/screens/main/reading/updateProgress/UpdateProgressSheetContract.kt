package com.nunez.libellis.ui.screens.main.reading.updateProgress

import com.nunez.libellis.entities.raw.Review
import com.nunez.libellis.entities.raw.UpdateProgress
import io.reactivex.Observable

interface UpdateProgressSheetContract {
    interface View {
        fun dismissSheet()
        fun setAuthorName(name: String)
        fun setBookTitle(title: String)
        fun showError()
        fun showLoading()
    }

    interface Presenter {
        fun onCancelClicked()
        fun onUpdateClicked(update: UpdateProgress)
        fun setBookInfo()
    }

    interface Interactor {
        fun getBookReadingInfo(reviewId: String): Observable<Review>
        fun updateBookReadingLocation(update: UpdateProgress)
        fun bookFinished(update: UpdateProgress)
    }
}