package com.nunez.libellis.views

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import com.nunez.libellis.R

/**
 * Created by paulnunez on 5/1/17
 */

class CustomFontEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {

        if (!isInEditMode)
            init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        if (!isInEditMode) {
            init(context)

            val fontFile: String

            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomFontEditText,
                    0, 0)
            try {
                fontFile = a.getString(R.styleable.CustomFontEditText_editTextFontFile)
                val t = Typeface.createFromAsset(context.assets, fontFile)
                this.typeface = t
            } finally {
                a.recycle()
            }

        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        if (!isInEditMode) {
            init(context)

            val fontName: String

            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomFontEditText,
                    0, 0)

            try {
                fontName = a.getString(R.styleable.CustomFontEditText_editTextFontFile)
                val t = Typeface.createFromAsset(context.assets, fontName)
                this.typeface = t
            } finally {
                a.recycle()
            }
        }
    }


    fun init(context: Context) {}
}