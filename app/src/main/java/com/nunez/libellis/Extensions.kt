package com.nunez.libellis

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by paulnunez on 4/25/17.
 */

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, duration).show()
}