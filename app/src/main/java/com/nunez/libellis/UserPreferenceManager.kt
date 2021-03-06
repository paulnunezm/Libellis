package com.nunez.libellis

import android.content.Context
import android.content.SharedPreferences

interface UserPreferenceManager {
    fun isUserLoggedIn(): Boolean
    fun isCurrentlyReadingBooksSavedInCache(): Boolean
    fun setReadingBooksSavedInCache()
}

class UserPrefsManager(private val context: Context) : UserPreferenceManager {

    private val preference: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.user_prefs), 0)

    val userId = preference.getString(getStringResource(R.string.user_prefs_user_id), "")

    override fun isUserLoggedIn(): Boolean = getBooleanPreference(R.string.user_prefs_logged_in)

    override fun isCurrentlyReadingBooksSavedInCache(): Boolean {
        return getBooleanPreference(R.string.user_prefs_currently_reading_cached)
    }

    private fun getStringResource(resource: Int): String = context.getString(resource)

    private fun getBooleanPreference(preferenceStringResource: Int,
                                     defaultValue: Boolean = false): Boolean {
        return preference.getBoolean(getStringResource(preferenceStringResource), defaultValue)
    }

    override fun setReadingBooksSavedInCache() {
        val editor = preference.edit()
        editor.putBoolean(getStringResource(R.string.user_prefs_currently_reading_cached), true)
        editor.apply()
    }

}