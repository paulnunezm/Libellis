package com.nunez.libellis.main.reading.updateProgress

import com.nunez.libellis.entities.Book
import com.nunez.libellis.entities.Review
import com.nunez.libellis.entities.ReviewBook
import com.nunez.libellis.entities.ReviewUserStatus
import io.reactivex.Observable

interface UpdateProgressSheetContract {
    interface View {
        fun dismissSheet()
        fun enableSeekBarListener()
        fun setAuthorName(name: String)
        fun setBookTitle(title: String)
        fun setMaxValue(value: Int)
        fun setPage(page: Int)
        fun setPercentage(percentage: Int)
        fun showLoading()
        fun showError()
    }

    interface Presenter {
        fun calculateValuesFromPages(page: Int)
        fun calculateValuesFromPercent(percent: Int)
        fun setReadingStatus(statuses: List<ReviewUserStatus>)
        fun setBookInformation(book: ReviewBook)
        fun onUpdateClicked()
        fun onCancelClicked()
    }

    interface Interactor {
        fun getBookReadingInfo(reviewId: String): Observable<Review>
        fun updateBookReadingLocation(): Observable<Unit>
    }
}