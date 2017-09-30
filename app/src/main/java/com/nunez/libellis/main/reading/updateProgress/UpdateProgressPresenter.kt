package com.nunez.libellis.main.reading.updateProgress

import android.util.Log
import com.nunez.libellis.entities.ReviewBook
import com.nunez.libellis.entities.ReviewUserStatus

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
                book.let { setBookInformation(it as ReviewBook) }
            }

            Log.d("presenter", "after")

        }, {
            //TODO: manage when the response doesn't returns the number of pages
            view.showError()
        }, {})
    }

    override fun setReadingStatus(statuses: List<ReviewUserStatus>) {
        view.setMaxValue(totalPages)

        if (statuses.isNotEmpty()) {
            val status = statuses[0]
            val page = status.page
            val percent = status.percent

            if (page != 0) {
                calculateValuesFromPages(page)
            } else {
                view.setMaxValue(100)
                calculateValuesFromPercent(percent)
            }
        } else {
            view.setPage(0)
            view.setPercentage(0)
        }
        view.enableSeekBarListener()
    }

    override fun setBookInformation(book: ReviewBook) {
        view.setAuthorName(book.authors?.get(0)?.name as String)
        view.setBookTitle(book.title)
    }

    override fun calculateValuesFromPages(page: Int) {
        val currentPage = page
        var currentPercent = 0

        if (totalPages != 0) currentPercent = page * 100 / totalPages
        view.setPage(currentPage)
        view.setPercentage(currentPercent)
    }

    override fun calculateValuesFromPercent(percent: Int) {
        val currentPage = percent * totalPages / 100
        view.setPage(currentPage)
        view.setPercentage(percent)
    }

    override fun onUpdateClicked() {}

    override fun onCancelClicked() {
        view.dismissSheet()
    }
}