package com.nunez.libellis.login

import android.content.Context
import android.content.SharedPreferences
import com.nunez.libellis.R

/**
 * Created by paulnunez on 4/25/17.
 */
class LoginInteractor (val context: Context): LoginContract.Interactor {

    var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.user_prefs), 0)

    override fun saveUserKeys(userKey: String?, userSecret: String?) {

        var editor = prefs.edit()

        editor.apply {
            putBoolean(context.getString(R.string.user_prefs_logged_in), true)
            putString(context.getString(R.string.user_prefs_user_key), userKey)
            putString(context.getString(R.string.user_prefs_user_secret), userSecret)
        }
        editor.apply()
    }
}