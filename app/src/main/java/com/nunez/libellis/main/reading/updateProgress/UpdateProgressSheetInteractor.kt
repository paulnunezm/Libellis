package com.nunez.libellis.main.reading.updateProgress

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nunez.libellis.entities.GoodreadsResponse
import com.nunez.libellis.entities.Review
import com.nunez.libellis.repository.GoodreadsService
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class UpdateProgressSheetInteractor : UpdateProgressSheetContract.Interactor {

    override fun getBookReadingInfo(reviewId: String): Observable<Review> {
        // TODO: check internet connection

        return Observable.create<Review>({
            subscriber ->

            Log.d("progress", " looking progress")

            val client = OkHttpClient().newBuilder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(GoodreadsService.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(client)
                    .build()

            val goodreads = retrofit.create(GoodreadsService::class.java)

            try {
                val response = goodreads.getReview(com.nunez.libellis.BuildConfig.GOODREADS_API_KEY, reviewId)

                response.enqueue(object : Callback<GoodreadsResponse> {

                    override fun onResponse(call: Call<GoodreadsResponse>?, response: Response<GoodreadsResponse>?) {
                        val review = response?.body()?.review

                        Log.i("progress", "onResponse ${review?.book?.title}")

                        if (review != null) {
                            Log.i("progress", "onNext")
                            subscriber.onNext(review)
                            subscriber.onComplete()
                        }else{
                            Log.e("progress", "Response error")
                            subscriber.onError(Throwable("No review founded"))
                        }
                    }

                    override fun onFailure(call: Call<GoodreadsResponse>?, t: Throwable?) {
                        Log.d("progress", "on failure -> ${t?.message}")
                        subscriber.onError(Throwable("No review founded"))

                    }
                })

            } catch (e: Throwable) {
                Log.e("progress", e.message)

                subscriber.onError(e)
            }
        })
    }

    override fun updateBookReadingLocation(): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}