package com.nunez.libellis.main.reading.updateProgress

import com.nunez.libellis.entities.Review
import com.nunez.libellis.entities.ReviewUserStatus
import com.nunez.libellis.entities.UpdateProgress
import io.reactivex.Completable
import io.reactivex.Observable

interface UpdateProgressSheetContract {
    interface View {
        fun dismissSheet()
        fun setAuthorName(name: String)
        fun setBookTitle(title: String)
        fun showCommentInput()
        fun showError()
        fun showLoading()
        fun showRatingStars()
    }

    interface Presenter {
        fun onCancelClicked()
        fun onUpdateClicked(update: UpdateProgress)
        fun onCommentClicked()
        fun onFinishClicked()
        fun setBookInfo(title: String, author: String)
        fun setReadingStatus(statuses: List<ReviewUserStatus>)
    }

    interface Interactor {
        fun getBookReadingInfo(reviewId: String): Observable<Review>
        fun updateBookReadingLocation(): Completable
    }
}