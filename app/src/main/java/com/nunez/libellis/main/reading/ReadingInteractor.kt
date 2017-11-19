package com.nunez.libellis.main.reading

import com.nunez.libellis.ConnectivityChecker
import com.nunez.libellis.entities.CurrentlyReadingBook
import com.nunez.libellis.entities.raw.Review
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.ReactiveStore
import io.reactivex.Observable

class ReadingInteractor
constructor(
//        val context: Context,
        private val repository: ReactiveStore<String, CurrentlyReadingBook>,
        private val connectivityChecker: ConnectivityChecker,
        private val goodreadsService: GoodreadsService
) : ReadingContract.Interactor {

    companion object {
        const val TAG = "ReadingInteractor"
    }

    override fun requestBooks(): Observable<List<Review>> {
        return Observable.defer {
//           try{
//               if (context.isConnectedToTheInternet()) {
                   requestBooksFromApi()
//               } else {
//                  throw NoConnectivityException()
//               }
//           }catch (e: NoConnectivityException){
//                Observable.error<List<Review>> { e }
//           }
        }
    }

    private fun requestBooksFromApi(): Observable<List<Review>> {
//        val retrofit = signedRetrofit.instance
//        val goodreads = retrofit.create(GoodreadsService::class.java)
//        return goodreads.getBooksOnShelfRX(
//                userId = context.getUserId(),
//                shelfName = "currently-reading")
//                .map {
//                    it.reviews ?: emptyList()
//                }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
        TODO()
    }

}