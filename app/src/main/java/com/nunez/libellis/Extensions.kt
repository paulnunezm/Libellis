package com.nunez.libellis

import android.support.design.widget.Snackbar
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by paulnunez on 4/25/17.
 */

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, duration).show()
}


// Retrofit callback
fun <T> Call<T>.enqueue(success: (response: Response<T>) -> Unit,
                        failure: (t: Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            success(response)
        }

        override fun onFailure(call: Call<T>?, t: Throwable) {
            failure(t)
        }
    })
}