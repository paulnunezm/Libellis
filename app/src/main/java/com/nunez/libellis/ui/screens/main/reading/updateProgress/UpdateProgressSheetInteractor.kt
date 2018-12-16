package com.nunez.libellis.ui.screens.main.reading.updateProgress

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.nunez.libellis.entities.raw.Review
import com.nunez.libellis.entities.raw.UpdateProgress
import com.nunez.libellis.ui.screens.main.reading.updateProgress.jobServices.UpdateProgressJobService
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.ui.views.UpdateProgressView
import io.reactivex.Observable


class UpdateProgressSheetInteractor(
        private val context: Context,
        private val goodreadsService: GoodreadsService
) : UpdateProgressSheetContract.Interactor {

    override fun getBookReadingInfo(reviewId: String): Observable<Review> {
        return Observable.just(Review())
    }

    override fun updateBookReadingLocation(update: UpdateProgress) {
        return if (update.type == UpdateProgressView.TYPE_PERCENT) {
            updateBookWithPercent(update)
        } else {
            updateBookWithPages(update)
        }
    }

    override fun bookFinished(update: UpdateProgress) {
        startUpdateProgressJob(update)
    }

    private fun updateBookWithPages(update: UpdateProgress) {
        startUpdateProgressJob(update)
    }

    private fun updateBookWithPercent(update: UpdateProgress) {
        startUpdateProgressJob(update)
    }

    private fun startUpdateProgressJob(update: UpdateProgress) {
        val serviceName = ComponentName(context, UpdateProgressJobService::class.java)
        val scheduler =  context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val bundle = update.toPersistableBundle()
        val id = scheduler.allPendingJobs.size + 1
        val jobInfo = JobInfo.Builder(id, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setExtras(bundle)
                .build()
        scheduler.schedule(jobInfo)
    }
}