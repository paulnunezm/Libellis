package com.nunez.libellis.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.nunez.libellis.R
import kotlinx.android.synthetic.main.update_progress_input.view.*

/**
 * Created by paulnunez on 10/6/17.
 */
class ProgressInput @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val INPUT_PERCENT = "percent"
        const val INPUT_PAGE = "page"
    }

    var currentInputType = INPUT_PERCENT
    var on100PercentListener: (() -> Unit)? = null
    var onTotalPagesListener: (() -> Unit)? = null
    var totalPages: Int = 0
    var currentPage = 0
        set(value) {
            field = value
            invokeListenerIfTotalPagesIsReached(value)
        }
    var currentPercent = 0
        set(value) {
            field = value
            invokeListenerIf100PercentIsReached(value)
        }

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.update_progress_input, this)

        setDefaultSelector(INPUT_PERCENT)
        setInputListener()
        setSelectorListeners()
    }

    fun setDefaultSelector(selector: String) {
        currentInputType = when (selector) {
            INPUT_PERCENT -> {
                setPercentHint()
                INPUT_PERCENT
            }
            INPUT_PAGE -> {
                setPageHint()
                INPUT_PAGE
            }
            else -> throw Exception("Invalid selector")
        }
        changeSelectorsColorState()
    }

    private fun setInputListener() {
        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val textInString = s.toString()
                if(textInString.isNotEmpty()){
                    val value = textInString.toInt()
                    if (currentInputType == INPUT_PERCENT) {
                        currentPercent = value
                    } else {
                        currentPage = value
                    }
                }
            }
        })
    }

    private fun setSelectorListeners() {
        pageSelector.setOnClickListener {
            currentInputType = INPUT_PAGE
            setPageHint()
            clearInput()
            changeSelectorsColorState()
        }
        percentSelector.setOnClickListener {
            currentInputType = INPUT_PERCENT
            setPercentHint()
            clearInput()
            changeSelectorsColorState()
        }
    }

    fun clearInput(){
        input.setText("",TextView.BufferType.EDITABLE)
    }

    private fun changeSelectorsColorState() {
        if (currentInputType == INPUT_PAGE) {
            setColorsWhenPageInputSelected()
        } else {
            setColorsWhenPercentInputSelected()
        }
    }

    private fun setColorsWhenPercentInputSelected() {
        pageSelector.setTextColor(resources.getColor(R.color.grey_lighter))
        percentSelector.setTextColor(resources.getColor(R.color.grey_dark))
    }

    private fun setColorsWhenPageInputSelected() {
        pageSelector.setTextColor(resources.getColor(R.color.grey_dark))
        percentSelector.setTextColor(resources.getColor(R.color.grey_lighter))
    }

    private fun invokeListenerIfTotalPagesIsReached(value: Int) {
        if (totalPages != 0 && value == totalPages) {
            onTotalPagesListener?.apply {
                invoke()
            }
        }
    }

    private fun invokeListenerIf100PercentIsReached(value: Int) {
        if (value >= 100)
            on100PercentListener?.apply {
                invoke()
            }
    }

    private fun setPercentHint() {
        setHint(INPUT_PERCENT)
    }

    private fun setPageHint() {
        setHint(INPUT_PAGE)
    }

    private fun setHint(inputType: String) {
        val hintResource: Int = if (inputType == INPUT_PERCENT) {
            R.string.progress_input_percent_symbol
        } else {
            R.string.progress_input_page_symbol
        }
        input.hint = resources.getString(hintResource)
    }
}