package com.nunez.libellis.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.nunez.libellis.R
import com.nunez.libellis.entities.UpdateProgress
import kotlinx.android.synthetic.main.update_progress_layout.view.*
import kotlinx.android.synthetic.main.update_progress_sheet_book_info.view.*

/**
 * Created by paulnunez on 10/11/17.
 */
class UpdateProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val TYPE_PERCENT = 0
        const val TYPE_PAGE = 1
    }

    var progressInput: ProgressInput
    private var currentProgressValue = ""

    var bookId = ""
    var title = ""
        set(value) {
            field = value
            bookTitle.text = value
        }
    var authorName = ""
        set(value) {
            field = value
            bookAuthor.text = value
        }
    var values = UpdateProgress()
        get() {
            var currentType = 0

            when (progressInput.currentInputType) {
                ProgressInput.INPUT_PAGE -> {
                    currentType = TYPE_PAGE
                    currentProgressValue = progressInput.currentPage.toString()
                }
                else -> {
                    currentType = TYPE_PERCENT
                    currentProgressValue = progressInput.currentPercent.toString()
                }
            }

            return UpdateProgress(
                    bookId,
                    currentType,
                    currentProgressValue,
                    commentEditText.text.toString(),
                    isFinished,
                    ratingView.getRating())
        }
    var isFinished = false

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.update_progress_layout, this)

        progressInput = findViewById(R.id.progressInputLayout)

        progressInputLayout.on100PercentListener = {
            onEndOfTheBookReached()
        }
        progressInputLayout.onTotalPagesListener = {
            onEndOfTheBookReached()
        }
        finishedButton.setOnClickListener {
            it.visibility = View.GONE
            progressInput.visibility = View.GONE
            showRating()
        }
        commentButton.setOnClickListener {
            commentEditText.visibility = View.VISIBLE
        }
    }

    private fun onEndOfTheBookReached() {
        isFinished = true
        hideInputAndShowRating()
    }

    private fun hideInputAndShowRating() {
        progressInputLayout.visibility = View.GONE
        showRating()
    }

    private fun showRating() {
        ratingView.visibility = View.VISIBLE
    }

}