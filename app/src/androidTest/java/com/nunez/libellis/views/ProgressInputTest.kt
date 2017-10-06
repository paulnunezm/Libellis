package com.nunez.libellis.views

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.LayoutInflater
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
    lateinit var input: TextView

    @Before
    fun setUp() {
        val view = LayoutInflater.from(mActivityRule.activity).inflate(R.layout.test_layout, null) as LinearLayout
        progressInput = ProgressInput(mActivityRule.activity)
        view.addView(progressInput)
        percentSelector = progressInput.findViewById(R.id.percentSelector)
        pageSelector = progressInput.findViewById(R.id.pageSelector)
        input = progressInput.findViewById(R.id.input)
    }

    @Test
    fun shouldShowPercentSelectorWhenSetAsDefault(){
        progressInput.setDefaultSelector(ProgressInput.INPUT_PERCENT)
        assertSelectorHasCorrectColors(percentSelector, pageSelector)
    }

    @Test
    fun shouldShowPageSelectorWhenSetAsDefault(){
        progressInput.setDefaultSelector(ProgressInput.INPUT_PAGE)
        assertSelectorHasCorrectColors(pageSelector, percentSelector)
    }

    @Test
    fun selectingPageShouldChangeSelectorColors() {
        pageSelector.performClick()
        assertSelectorHasCorrectColors(pageSelector, percentSelector)
    }

    @Test
    fun selectingPercentShouldChangeSelectorColors() {
        percentSelector.performClick()
        assertSelectorHasCorrectColors(percentSelector, pageSelector)
    }

    @Test
    fun shouldShowCorrectPercentWhenSelected() {
        // given
        progressInput.currentPercent = 50

        // when
        percentSelector.performClick()

        //then
        assertEquals(50, input.text.toString().toInt())
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
    fun shouldShowCorrectPageWhenSelected() {
        // given
        progressInput.currentPercent = 27
        progressInput.currentPage = 25

        // when
        percentSelector.performClick()
        pageSelector.performClick()

        //then
        assertEquals(25, input.text.toString().toInt())
    }

    private fun assertSelectorHasCorrectColors(selectedSelector: TextView, nonSelectedSelector: TextView) {
        val resources = mActivityRule.activity.resources
        assertEquals(selectedSelector.currentTextColor, resources.getColor(R.color.grey_dark))
        assertEquals(nonSelectedSelector.currentTextColor, resources.getColor(R.color.grey_lighter))
    }
}