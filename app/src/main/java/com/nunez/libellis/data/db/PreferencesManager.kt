package com.nunez.libellis.data.db

import android.content.Context
import com.nunez.libellis.R

class PreferencesManager(val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(context.getString(R.string.user_prefs),0)
    private val editor = sharedPreferences.edit()

    var userId: String
        get() = sharedPreferences.getString(context.getString(R.string.user_prefs_user_id), "") as String
        set(value) {
            editor.apply {
                putString(context.getString(R.string.user_prefs_user_id), value)
                apply()
            }
        }

    var isUserLogged: Boolean
        get() = sharedPreferences.getBoolean(context.getString(R.string.user_prefs_logged_in), false)
        set(value) {
            editor.putBoolean(context.getString(R.string.user_prefs_logged_in), value)
                    .apply()
        }

    val userKey: String
        get() = sharedPreferences.getString(context.getString(R.string.user_prefs_user_key), "") as String

    val userSecret: String
        get() = sharedPreferences.getString(context.getString(R.string.user_prefs_user_secret), "") as String

    fun saveUserKeys(userKey: String, userSecret: String) {
        editor.apply {
            putString(context.getString(R.string.user_prefs_user_key), userKey)
            putString(context.getString(R.string.user_prefs_user_secret), userSecret)
                    .apply()
        }
    }
}