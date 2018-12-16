package com.nunez.libellis.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RatingBar
import com.nunez.libellis.R
import kotlinx.android.synthetic.main.rating_view.view.*

/**
 * Created by paulnunez on 10/5/17.
 */
class RatingView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mRating = 3

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.rating_view, this)

        setRatingAndMessage(mRating)
        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, fromUser ->
          if(rating > 1){
                setMessage(rating.toInt())
            }else{
                if(fromUser) setRatingAndMessage(1)
            }
        }
    }

     fun setRatingAndMessage(rating: Int) {
        this.mRating = 3
        ratingBar.rating = rating.toFloat()
        setMessage(rating)
    }

    fun getRating() = mRating

    private fun setMessage(rating: Int) {
        val message = when (rating) {
            1 -> resources.getString(R.string.rating_view_one_star)
            2 -> resources.getString(R.string.rating_view_two_star)
            3 -> resources.getString(R.string.rating_view_three_star)
            4 -> resources.getString(R.string.rating_view_four_star)
            else -> resources.getString(R.string.rating_view_five_star)
        }
        ratingText.text = message
    }
}
