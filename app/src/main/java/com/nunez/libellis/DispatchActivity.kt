package com.nunez.libellis

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nunez.libellis.login.LoginActivity
import com.nunez.libellis.main.MainActivity

class DispatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nextActivityIntent =
                if (userLoggedIn())
                    Intent(this, MainActivity::class.java)
                else
                    Intent(this, LoginActivity::class.java)

        nextActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(nextActivityIntent)
    }

    fun userLoggedIn() = this.getSharedPreferences(getString(com.nunez.libellis.R.string.user_prefs), 0)
            .getBoolean(getString(R.string.user_prefs_logged_in), false)

}
