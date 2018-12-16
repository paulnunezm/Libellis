package com.nunez.libellis.ui.views

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.nunez.libellis.R
import com.nunez.libellis.TestActivity
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by paulnunez on 10/6/17.
 */
@RunWith(AndroidJUnit4::class)
class ProgressInputTest {

    @get: Rule
    var mActivityRule = ActivityTestRule(TestActivity::class.java)

    lateinit var progressInput: ProgressInput
    lateinit var percentSelector: TextView
    lateinit var pageSelector: TextView
    lateinit var input: EditText

    lateinit var percentHint: String
    lateinit var pageHint: String

    @Before
    fun setUp() {
        val view = LayoutInflater.from(mActivityRule.activity).inflate(R.layout.test_layout, null) as LinearLayout
        progressInput = ProgressInput(mActivityRule.activity)
        view.addView(progressInput)

        percentSelector = progressInput.findViewById(R.id.percentSelector)
        pageSelector = progressInput.findViewById(R.id.pageSelector)
        input = progressInput.findViewById(R.id.input)

        percentHint = mActivityRule.activity.resources.getString(R.string.progress_input_percent_symbol)
        pageHint = mActivityRule.activity.resources.getString(R.string.progress_input_page_symbol)
    }

    @Test
    fun shouldStartwithPercentSelectorAsDefault(){
        assertSelectorHasCorrectColors(percentSelector, pageSelector)
    }

    @Test
    fun shouldStartwithPercentHintAsDefault(){
        assertCorrectHintIsSet(percentHint)
    }

    @Test
    fun shouldShowPercentSelectorAndHintWhenSetAsDefault(){
        progressInput.setDefaultSelector(ProgressInput.INPUT_PERCENT)
        assertSelectorHasCorrectColors(percentSelector, pageSelector)
        assertCorrectHintIsSet(percentHint)
    }

    @Test
    fun shouldShowPageSelectorAndHintWhenSetAsDefault(){
        progressInput.setDefaultSelector(ProgressInput.INPUT_PAGE)
        assertSelectorHasCorrectColors(pageSelector, percentSelector)
        assertCorrectHintIsSet(pageHint)
    }

    @Test
    fun selectingPageShouldChangeSelectorColorsAndHint() {
        pageSelector.performClick()
        assertSelectorHasCorrectColors(pageSelector, percentSelector)
        assertCorrectHintIsSet(pageHint)
    }

    @Test
    fun selectingPercentShouldChangeSelectorColorsAndHint() {
        percentSelector.performClick()
        pageSelector.performClick()
        percentSelector.performClick()

        assertSelectorHasCorrectColors(percentSelector, pageSelector)
        assertCorrectHintIsSet(percentHint)
    }

    @Test
    fun ifCurrentPercentIs100AListenerShouldBeInvoked() {
        // given
        var listenerInvoked = false
        progressInput.currentPercent = 0
        progressInput.on100PercentListener = {
            listenerInvoked = true
        }

        // when
        progressInput.currentPercent = 100

        //then
        assertTrue(listenerInvoked)
    }

    @Test
    fun ifCurrentPercentIsNot100TheListenerShouldNotBeInvoked() {
        // given
        var listenerInvoked = false
        progressInput.currentPercent = 0
        progressInput.on100PercentListener = {
            listenerInvoked = true
        }

        // when
        progressInput.currentPercent = 99

        //then
        assertFalse(listenerInvoked)
    }

    @Test
    fun if100PercentListenerIsNotSetShouldNotThrowNullPointerException() {
        progressInput.currentPercent = 0

        progressInput.currentPercent = 100
    }

    @Test
    fun ifBookTotalPageIsSetAndCurrentPageIsSetEqualsShouldInvokeListener() {
        var listenerInvoked = false
        val totalBookPages = 400
        progressInput.currentPage = 0
        progressInput.totalPages = totalBookPages
        progressInput.onTotalPagesListener = {
            listenerInvoked = true
        }

        progressInput.currentPage = totalBookPages

        assertTrue(listenerInvoked)
    }

    @Test
    fun ifBookTotalPageIsNotReachedShouldNotInvokeListener() {
        var listenerInvoked = false
        val totalBookPages = 400
        progressInput.currentPage = 0
        progressInput.totalPages = 399
        progressInput.onTotalPagesListener = {
            listenerInvoked = true
        }

        progressInput.currentPage = totalBookPages

        assertFalse(listenerInvoked)
    }

    @Test
    fun shouldShowEraseInputWhenPageWhenSelected() {
        // given
        progressInput.currentPercent = 27
        progressInput.currentPage = 25

        // when
        percentSelector.performClick()
        pageSelector.performClick()

        //then
        assertTrue (input.text.toString().isEmpty())
    }


    @Test
    fun shouldShowEraseInputPercentWhenSelected() {
        // given
        progressInput.currentPercent = 50

        // when
        percentSelector.performClick()

        //then
        assertTrue (input.text.toString().isEmpty())
    }


    private fun assertSelectorHasCorrectColors(selectedSelector: TextView, nonSelectedSelector: TextView) {
        val resources = mActivityRule.activity.resources
        assertEquals(selectedSelector.currentTextColor, resources.getColor(R.color.grey_dark))
        assertEquals(nonSelectedSelector.currentTextColor, resources.getColor(R.color.grey_lighter))
    }

    private fun assertCorrectHintIsSet(hint: String){
        assertEquals(hint, input.hint.toString())
    }
}