package com.nunez.libellis.main.updates

import android.content.Context
import com.nunez.libellis.BuildConfig
import com.nunez.libellis.R
import com.nunez.libellis.enqueue
import com.nunez.libellis.entities.GoodreadsResponse
import com.nunez.libellis.entities.Shelve
import com.nunez.libellis.entities.Update
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedHttpClient
import com.nunez.libellis.repository.parsers.UpdatesParser
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.IOException
import javax.inject.Inject


class UpdatesInteractor
@Inject constructor(val context: Context) : UpdatesContract.Interactor {

    override fun requestUpdates(): Observable<List<Update>> {
        // TODO: check internet connection

        val client = SignedHttpClient(context).instance
        val request = Request.Builder()
                .url(GoodreadsService.BASE_URL + GoodreadsService.UPDATES_ENDPOINT)
                .build()

        return Observable.create<Response>({ subscriber ->
            try {
                val response = client.newCall(request).execute()
                subscriber.onNext(response)
                subscriber.onComplete()

                if (!response.isSuccessful) subscriber.onError(Exception("error"))
            } catch (e: IOException) {
                subscriber.onError(e)
            }

        }).map {
            UpdatesParser(it.body().string()).parse()
        }
//        .flatMap { it }  emits one Update item until reach the end
    }

    override fun requestUserShelves(): Observable<List<Shelve>> {
        // TODO: check internet connection
        var shelves = emptyList<Shelve>()

        val userId = context.getSharedPreferences(context.getString(R.string.user_prefs), 0)
                .getString(context.getString(R.string.user_prefs_user_id), "")

        val apiKey = BuildConfig.GOODREADS_API_KEY

        if (userId.isEmpty()) throw IllegalStateException("No user id saved")


        return Observable.create<List<Shelve>>(
                { subscriber ->
                    val retrofit = Retrofit.Builder()
                            .baseUrl(GoodreadsService.BASE_URL)
                            .addConverterFactory(SimpleXmlConverterFactory.create())
                            .build()

                    val goodreads = retrofit.create(GoodreadsService::class.java)

                    try {
                        val response: Call<GoodreadsResponse> = goodreads.getShelves(userId, 1, apiKey)

                        response.enqueue(
                                {
                                    response: retrofit2.Response<GoodreadsResponse> ->
                                    if (response.isSuccessful) {
                                        subscriber.onNext(response.body().shelves)
                                        subscriber.onComplete()
                                    } else {
                                        subscriber.onError(Throwable("unsuccesfull request"))
                                    }
                                },
                                {
                                    t ->
                                    subscriber.onError(t)
                                })
                    } catch (e: Exception) {
                        subscriber.onError(e)
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }
}