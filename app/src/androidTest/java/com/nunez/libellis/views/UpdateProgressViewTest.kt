package com.nunez.libellis.views

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.nunez.libellis.R
import com.nunez.libellis.TestActivity
import junit.framework.Assert.*
import kotlinx.android.synthetic.main.update_progress_layout.view.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by paulnunez on 10/11/17.
 */
@RunWith(AndroidJUnit4::class)
class UpdateProgressViewTest {

    @get: Rule
    var mActivityRule = ActivityTestRule(TestActivity::class.java)

    private lateinit var progressView: UpdateProgressView
    private lateinit var inputView: ProgressInput
    private lateinit var ratingLayout: RatingView
    private val testTitle = "Murder in the Orient Express"
    private val testAuthor = "Agatha Christie"

    @Before
    fun setUp() {
        val view = LayoutInflater.from(mActivityRule.activity).inflate(R.layout.test_layout, null) as LinearLayout

        progressView = UpdateProgressView(mActivityRule.activity)

        ratingLayout = progressView.findViewById<RatingView>(R.id.ratingView)
        inputView = progressView.findViewById<ProgressInput>(R.id.progressInputLayout)

        view.addView(progressView)

        progressView.apply {
            title = testTitle
            authorName = testAuthor
        }
    }

    @Test
    fun shouldSetTheCorrectNames() {
        val title = progressView.findViewById<TextView>(R.id.bookTitle)
        val author = progressView.findViewById<TextView>(R.id.bookAuthor)

        assertEquals(testTitle, title.text)
        assertEquals(testAuthor, author.text)
    }

    @Test
    fun when100PercentEnteredShouldShowRatingStarsAndHideInput() {
        inputView.currentPercent = 100

        assertViewIsGone(inputView)
        assertViewIsVisible(ratingLayout)
    }

    @Test
    fun when100PercentEnteredIsFinishVarShouldBeTrue() {
        inputView.currentPercent = 100

        assertTrue(progressView.isFinished)
    }

    @Test
    fun whenNot100PercentEnteredIsFinishVarShouldBeFals() {
        inputView.currentPercent = 10

        assertFalse(progressView.isFinished)
    }

    @Test
    fun whenTotalBookPagesReachedShouldShowRatingStarsAndHideInput() {
        inputView.totalPages = 200
        inputView.currentPage = 200

        assertViewIsGone(inputView)
        assertViewIsVisible(ratingLayout)
    }

    @Test
    fun whenTotalBookPagesReachedIsFinishedShouldBeTrue() {
        inputView.totalPages = 200
        inputView.currentPage = 200

        assertTrue(progressView.isFinished)
    }

    @Test
    fun whenNotTotalBookPagesReachedIsFinishedShouldBeFalse() {
        inputView.totalPages = 200
        inputView.currentPage = 20

        assertFalse(progressView.isFinished)
    }

    @Test
    fun whenIveFinishedIsClickedShowRatingStarsAndHideFinishedButton() {
        val finished = progressView.findViewById<View>(R.id.finishedButton)

        finished.performClick()

        assertViewIsGone(finished)
        assertViewIsGone(ratingLayout)
    }

    @Test
    fun whenIveFinishedIsClickedShouldHideInput() {
        val finished = progressView.findViewById<View>(R.id.finishedButton)

        finished.performClick()

        assertViewIsGone(finished)
        assertViewIsGone(inputView)
    }

    @Test
    fun whenCommentIsClickedShouldShowTheCommentInputText() {
        val comment = progressView.findViewById<View>(R.id.commentButton)
        val commentInputText = progressView.findViewById<EditText>(R.id.commentEditText)

        comment.performClick()

        assertViewIsVisible(commentInputText)
    }

    @Test
    fun shouldReturnCorrectValuesWhenUsingPercent() {
        val comment = "It was awesome"
        val percent = 70
        prepareViewWithProperties(comment = comment, atPercent = percent)

        val values = progressView.values

        assertEquals(UpdateProgressView.TYPE_PERCENT, values.type)
        assertEquals(percent.toString(), values.value)
        assertEquals(comment, values.comment)
        assertFalse(values.isFinished)
    }

    @Test
    fun shouldReturnCorrectValuesWhenUsingPercentAndIsFinished() {
        val comment = "It was awesome"
        val percent = 100
        prepareViewWithProperties(comment = comment, atPercent = percent)

        val values = progressView.values

        assertEquals(UpdateProgressView.TYPE_PERCENT, values.type)
        assertEquals(percent.toString(), values.value)
        assertEquals(comment, values.comment)
        assertTrue(values.isFinished)
    }

    @Test
    fun shouldReturnCorrectValuesWhenUsingPages() {
        val comment = "It was awesome"
        val page = 70
        val tPages = 100
        prepareViewWithProperties(comment, 0, page, tPages, ProgressInput.INPUT_PAGE)

        val values = progressView.values

        assertEquals(UpdateProgressView.TYPE_PAGE, values.type)
        assertEquals(page.toString(), values.value)
        assertEquals(comment, values.comment)
        assertFalse(values.isFinished)
    }

    @Test
    fun shouldReturnCorrectValuesWhenUsingPagesAndTotalReached() {
        val comment = "It was awesome"
        val page = 100
        val tPages = 100
        prepareViewWithProperties(comment, 0, page, tPages, ProgressInput.INPUT_PAGE)

        val values = progressView.values

        assertEquals(UpdateProgressView.TYPE_PAGE, values.type)
        assertEquals(page.toString(), values.value)
        assertEquals(comment, values.comment)
        assertTrue(values.isFinished)
    }

    private fun assertViewIsGone(view: View) {
        assertEquals(View.GONE, view.visibility)
    }

    private fun assertViewIsVisible(view: View) {
        assertEquals(View.VISIBLE, view.visibility)
    }

    private fun prepareViewWithProperties(comment: String = "", atPercent: Int = 0, atPage: Int = 0,
                                          bookTotalPages: Int = 0, inputType: String = ProgressInput.INPUT_PERCENT) {
        with(inputView) {
            currentInputType = inputType
            totalPages = bookTotalPages
            currentPage = atPage
            currentPercent = atPercent
        }
        progressView.commentEditText.setText(comment, TextView.BufferType.EDITABLE)
    }
}