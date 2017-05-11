package com.nunez.libellis

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nunez.libellis.login.LoginActivity
import com.nunez.libellis.main.MainActivity

class DispatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch)

        val nextActivityIntent =
                if (userLoggedIn())
                    Intent(this, MainActivity::class.java)
                else
                    Intent(this, LoginActivity::class.java)

        nextActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        nextActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(nextActivityIntent)
    }

    fun userLoggedIn() = this.getSharedPreferences(getString(com.nunez.libellis.R.string.user_prefs), 0)
            .getBoolean(getString(R.string.user_prefs_logged_in), false)

}
