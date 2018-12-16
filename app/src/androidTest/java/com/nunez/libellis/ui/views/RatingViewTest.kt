package com.nunez.libellis.ui.views

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.nunez.libellis.R
import com.nunez.libellis.TestActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by paulnunez on 10/5/17.
 */
@RunWith(AndroidJUnit4::class)
class RatingViewTest {

    @get: Rule
    var mActivityRule = ActivityTestRule(TestActivity::class.java)


    lateinit var ratingView: RatingView
    lateinit var ratingBar: RatingBar
    lateinit var message: TextView

    @Before
    fun setUp() {
        val view = LayoutInflater.from(mActivityRule.activity).inflate(R.layout.test_layout, null) as LinearLayout
        ratingView = RatingView(mActivityRule.activity)
        view.addView(ratingView)

        ratingBar = ratingView.findViewById<RatingBar>(R.id.ratingBar)
        message = ratingView.findViewById<TextView>(R.id.ratingText)
    }

    @Test
    fun shouldShowIHatedWithOneStar() {
        val rating = 1
        ratingView.setRatingAndMessage(rating)
        assertMessageAreEqualsAndReturnsCorrectRating(R.string.rating_view_one_star, rating)
    }


    @Test
    fun shouldShowDislikeditWithTwoStars() {
        val rating = 2
        ratingView.setRatingAndMessage(rating)
        assertMessageAreEqualsAndReturnsCorrectRating(R.string.rating_view_two_star, rating)
    }


    @Test
    fun shouldShowItsOkWithThreeStars() {
        val rating = 3
        ratingView.setRatingAndMessage(rating)
        assertMessageAreEqualsAndReturnsCorrectRating(R.string.rating_view_three_star, rating)
    }


    @Test
    fun shouldShowILikedItWithFourStars() {
        val rating = 4
        ratingView.setRatingAndMessage(rating)
        assertMessageAreEqualsAndReturnsCorrectRating(R.string.rating_view_four_star, rating)
    }


    @Test
    fun shouldShowILovedItWithFiveStars() {
        val rating = 5
        ratingView.setRatingAndMessage(rating)
        assertMessageAreEqualsAndReturnsCorrectRating(R.string.rating_view_five_star, rating)
    }

    private fun assertMessageAreEqualsAndReturnsCorrectRating(expectedStringId: Int, rating: Int) {
        Assert.assertEquals("Message",
                mActivityRule.activity.resources.getString(expectedStringId),
               message.text.toString())
        Assert.assertEquals("Rating",rating, ratingBar.rating.toInt())
    }
}