package com.nunez.libellis.main.reading

import com.nunez.libellis.ConnectivityChecker
import com.nunez.libellis.EntityMapperBehavior
import com.nunez.libellis.entities.CurrentlyReadingBook
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.ReactiveStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReadingInteractor(
        private val userId: String,
        private val repository: ReactiveStore<String, CurrentlyReadingBook>,
        private val mapper: EntityMapperBehavior,
        private val connectivityChecker: ConnectivityChecker,
        private val goodreadsService: GoodreadsService
) : ReadingContract.Interactor {

    companion object {
        const val TAG = "ReadingInteractor"
    }

    override fun requestBooks(): Flowable<List<CurrentlyReadingBook>> =
            repository.all

    override fun fetchBooks(): Completable {
        return Completable.create({ emmiter ->
            goodreadsService.getBooksOnShelfRX(userId = userId, shelfName = "currently-reading")
                    .map { mapper.mapReviewListToCurrentlyReading(it.reviews) }
                    .subscribe({
                        repository.replaceAll(it)
                        emmiter.onComplete()
                    }, {
                        emmiter.onError(it)
                    })
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}