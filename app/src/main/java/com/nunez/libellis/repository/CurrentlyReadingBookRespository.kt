package com.nunez.libellis.repository

import com.nunez.libellis.UserPreferenceManager
import com.nunez.libellis.entities.CurrentlyReadingBook
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryAllAsFlowable
import com.vicpin.krealmextensions.saveAll
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class CurrentlyReadingBookRespository(
        private val realm: Realm,
        private val userPreferenceManager: UserPreferenceManager
) : ReactiveStore<String, CurrentlyReadingBook> {

    companion object {
        val TAG = "BookRespository"
    }

    @Deprecated("Not implemented")
    override fun getSingular(modelKey: String): Single<CurrentlyReadingBook> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val all: Flowable<List<CurrentlyReadingBook>>
        get() = CurrentlyReadingBook().queryAllAsFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    override fun replaceAll(modelList: List<CurrentlyReadingBook>): Completable {
        return Completable.create {
            CurrentlyReadingBook().deleteAll()
            modelList.saveAll()
            userPreferenceManager.setReadingBooksSavedInCache()
            it.onComplete()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun storeAll(modelList: List<CurrentlyReadingBook>): Completable {
        deleteAllCurrentlyReadingBooks()

        return Completable.create {
            modelList.saveAll()
            userPreferenceManager.setReadingBooksSavedInCache()
            it.onComplete()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun deleteAllCurrentlyReadingBooks() {
        realm.beginTransaction()
        realm.delete(CurrentlyReadingBook::class.java)
        realm.commitTransaction()
    }

    @Deprecated("Not implemented")
    override fun storeSingular(model: CurrentlyReadingBook): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}