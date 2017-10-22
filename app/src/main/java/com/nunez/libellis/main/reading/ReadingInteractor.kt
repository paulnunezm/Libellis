package com.nunez.libellis.main.reading

import android.content.Context
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.R
import com.nunez.libellis.enqueue
import com.nunez.libellis.entities.GoodreadsResponse
import com.nunez.libellis.entities.Review
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedRetrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

class ReadingInteractor
constructor(
        val context: Context,
        val signedRetrofit: SignedRetrofit
) : ReadingContract.Interactor {

    companion object {
        const val TAG = "ReadingInteractor"
    }

    override fun requestBooks(): Observable<List<Review>> {
        val retrofit = signedRetrofit.instance

        return Observable.create<List<Review>>({ subscriber ->

            val goodreads = retrofit.create(GoodreadsService::class.java)

            val userId = context.getSharedPreferences(context.getString(R.string.user_prefs), 0)
                    .getString(context.getString(R.string.user_prefs_user_id), "")

            val response: Call<GoodreadsResponse> = goodreads.getBooksOnShelf(
                    userId = userId,
                    shelfName = "currently-reading",
                    search = "",
                    developerKey = BuildConfig.GOODREADS_API_KEY)

            try {
                response.enqueue({ response ->
                    if (response.isSuccessful) {
                        val r = response.body()?.reviews as List<Review>
                        subscriber.onNext(r)
                        subscriber.onComplete()
                    } else {
                        subscriber.onError(Throwable("Response unsuccessful"))
                    }
                }, {
                    subscriber.onError(it)
                })
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }
}