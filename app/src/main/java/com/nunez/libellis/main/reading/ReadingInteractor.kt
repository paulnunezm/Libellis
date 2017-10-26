package com.nunez.libellis.main.reading

import android.content.Context
import com.nunez.libellis.NoConnectivityException
import com.nunez.libellis.entities.Review
import com.nunez.libellis.getUserId
import com.nunez.libellis.isConnectedToTheInternet
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedRetrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReadingInteractor
constructor(
        val context: Context,
        private val signedRetrofit: SignedRetrofit
) : ReadingContract.Interactor {

    companion object {
        const val TAG = "ReadingInteractor"
    }

    override fun requestBooks(): Observable<List<Review>> {
        return Observable.defer {
           try{
               if (context.isConnectedToTheInternet()) {
                   requestBooksFromApi()
               } else {
                  throw NoConnectivityException()
               }
           }catch (e: NoConnectivityException){
                Observable.error<List<Review>> { e }
           }
        }
    }

    private fun requestBooksFromApi(): Observable<List<Review>> {
        val retrofit = signedRetrofit.instance
        val goodreads = retrofit.create(GoodreadsService::class.java)
        return goodreads.getBooksOnShelfRX(
                userId = context.getUserId(),
                shelfName = "currently-reading")
                .map {
                    it.reviews ?: emptyList()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}