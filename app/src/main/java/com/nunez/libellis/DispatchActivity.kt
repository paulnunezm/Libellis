package com.nunez.libellis

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class DispatchActivity : AppCompatActivity() {

    lateinit var nextActivityIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch)

        if(userLoggedIn()){
            // go to updates activity
        }else{
            // go to login activity
        }

        startActivity(nextActivityIntent)
    }

    fun userLoggedIn() = this.getSharedPreferences("user_prefs", 0)
            .getBoolean("logged_in", false)

}
