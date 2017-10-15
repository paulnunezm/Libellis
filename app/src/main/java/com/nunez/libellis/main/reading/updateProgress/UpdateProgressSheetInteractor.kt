package com.nunez.libellis.main.reading.updateProgress

import com.nunez.libellis.entities.GoodreadsResponse
import com.nunez.libellis.entities.Review
import com.nunez.libellis.entities.UpdateProgress
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.views.UpdateProgressView
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateProgressSheetInteractor(
        private val goodreadsService: GoodreadsService
) : UpdateProgressSheetContract.Interactor {

    override fun getBookReadingInfo(reviewId: String): Observable<Review> {
        // TODO: check internet connection

        return Observable.create<Review>({ subscriber ->
            try {
                val response = goodreadsService.getReview(com.nunez.libellis.BuildConfig.GOODREADS_API_KEY, reviewId)
                response.enqueue(object : Callback<GoodreadsResponse> {
                    override fun onResponse(call: Call<GoodreadsResponse>?, response: Response<GoodreadsResponse>?) {
                        val review = response?.body()?.review
                        if (review != null) {
                            subscriber.onNext(review)
                            subscriber.onComplete()
                        } else {
                            subscriber.onError(Throwable("No review founded"))
                        }
                    }
                    override fun onFailure(call: Call<GoodreadsResponse>?, t: Throwable?) {
                        t?.printStackTrace()
                        subscriber.onError(Throwable("No review founded"))
                    }
                })
            } catch (e: Throwable) {
                e.printStackTrace()
                subscriber.onError(e)
            }
        })
    }

    override fun updateBookReadingLocation(update: UpdateProgress): Completable {
        if (update.type == UpdateProgressView.TYPE_PERCENT) {
            return updateBookWithPercent(update)
        } else {
           return updateBookWithPages(update)
        }
    }

    override fun bookFinished(update: UpdateProgress): Completable {
        return goodreadsService.sendBookFinished(
                update.id,
                update.comment,
                update.rating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun updateBookWithPages(update: UpdateProgress): Completable {
        return goodreadsService.sendBookUpdate(
                id = update.id,
                page = update.value,
                comment = update.comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun updateBookWithPercent(update: UpdateProgress): Completable {
        return goodreadsService.sendBookUpdate(
                id = update.id,
                percent = update.value,
                comment = update.comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}