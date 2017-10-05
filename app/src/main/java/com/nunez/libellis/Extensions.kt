package com.nunez.libellis

import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Useful extensions
 */

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, duration).show()
}

fun ViewGroup.inflate(resource: Int): View {
    return LayoutInflater.from(this.context).inflate(resource, this, false)
}


fun ImageView.loadImage(url: String){
    if(url.isNotEmpty()) Picasso.with(this.context).load(url).fit().into(this)
}



// Retrofit callback
inline fun <T> Call<T>.enqueue(crossinline success: (response: Response<T>) -> Unit,
                               crossinline failure: (t: Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            success(response)
        }

        override fun onFailure(call: Call<T>?, t: Throwable) {
            failure(t)
        }
    })
}