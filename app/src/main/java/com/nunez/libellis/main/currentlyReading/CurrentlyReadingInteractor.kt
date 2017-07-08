package com.nunez.libellis.main.currentlyReading

import android.content.Context
import com.nunez.libellis.entities.Review
import com.nunez.libellis.repository.SignedRetrofit
import io.reactivex.Observable
import javax.inject.Inject

class CurrentlyReadingInteractor
@Inject constructor(
        val context: Context,
        val signedRetrofit: SignedRetrofit
) : CurrentlyReadingContract.Interactor {

    override fun requestBooks(): Observable<List<Review>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        /*
        Log.d(TAG, "creating observable")
        Observable.create<Unit>({
            val retrofit = Retrofit.Builder()
                    .baseUrl(GoodreadsService.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build()

            val goodreads = retrofit.create(GoodreadsService::class.java)
            val userId = context.getSharedPreferences(context.getString(R.string.user_prefs), 0)
                    .getString(context.getString(R.string.user_prefs_user_id), "")

            val apiKey = BuildConfig.GOODREADS_API_KEY

            try {
                val response: Call<GoodreadsResponse> = goodreads.getBooksOnShelf(
                        userId = userId,
                        shelfName = "currently-reading",
                        search = "",
                        developerKey = apiKey
                )

                response.enqueue(
                        {
                            response: retrofit2.Response<GoodreadsResponse> ->
                            if (response.isSuccessful) {
                                val r: String = response.body()?.reviews?.get(0)?.book.toString()
                                Log.d(TAG,"sucessfull $r" )
                            } else {
                                Log.d(TAG, "not successfull ${response.body().toString()}")
                            }
                        },
                        {
                            t ->
                            Log.e(TAG, t.message, t)
                        })
            } catch (e: Throwable) {
                Log.e(TAG, e.message, e)
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({},{})

         */
    }
}