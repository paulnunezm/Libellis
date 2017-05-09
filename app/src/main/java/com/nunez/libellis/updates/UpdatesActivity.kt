package com.nunez.libellis.updates

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nunez.libellis.R
import com.nunez.libellis.enqueue
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedRetrofit
import kotlinx.android.synthetic.main.updates_activity.*

class UpdatesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.updates_activity)

        button.setOnClickListener { requestXml() }
    }

    fun requestXml() {
        val retrofit = SignedRetrofit(this).instance

        val goodreadsService = retrofit.create(GoodreadsService::class.java)
        val retrofitCall = goodreadsService.getUpdates()

        retrofitCall.enqueue(
                { response ->
                    textView.text = "request finished"
                },
                { failure ->
                    textView.text = failure.message
                })

    }
}
