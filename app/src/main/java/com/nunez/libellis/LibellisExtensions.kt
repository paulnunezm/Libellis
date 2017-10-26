package com.nunez.libellis

import android.content.Context

/**
 * Created by paulnunez on 10/25/17.
 */

fun Context.getUserId(): String {
    return this.getSharedPreferences(this.getString(R.string.user_prefs), 0)
            .getString(this.getString(R.string.user_prefs_user_id), "")
}
