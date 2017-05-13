package com.nunez.libellis.main.updates

import android.app.Activity
import android.content.Context
import com.nunez.libellis.R
import com.nunez.libellis.entities.Update
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedHttpClient
import com.nunez.libellis.repository.parsers.UpdatesParser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class UpdatesInteractor(val context: Context) : UpdatesContract.Interactor {

    lateinit var updatesPresenter: UpdatesContract.Presenter

    override fun setPresenter(presenter: UpdatesContract.Presenter?) {
        presenter?.let {
            updatesPresenter = presenter
        }
    }

    override fun requestUpdates() {
        // TODO: check internet connection

        val client = SignedHttpClient(context).instance
        val request = Request.Builder()
                .url(GoodreadsService.BASE_URL + GoodreadsService.UPDATES_ENDPOINT)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                updatesPresenter.showError(context.getString(R.string.error_msg_implicit_error))
            }

            override fun onResponse(call: Call?, response: Response?) {
                response?.let {
                    var updates = ArrayList<Update>()
                    if (response.isSuccessful){
                        updates = UpdatesParser(response.body().string()).parse()
                    }

                    (context as Activity).runOnUiThread {
                        updatesPresenter.sendUpdates(updates)
                    }
                }
            }
        })
    }

}