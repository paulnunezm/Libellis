package com.nunez.libellis.main.updates

import android.content.Context
import com.nunez.libellis.entities.Update
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedHttpClient
import com.nunez.libellis.repository.parsers.UpdatesParser
import io.reactivex.Observable
import okhttp3.Request
import okhttp3.Response
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

        return Observable.create<Response>( { subscriber ->
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

}