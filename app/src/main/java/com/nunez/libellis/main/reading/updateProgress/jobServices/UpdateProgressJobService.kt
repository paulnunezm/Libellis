package com.nunez.libellis.main.reading.updateProgress.jobServices

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.nunez.libellis.entities.UpdateProgress
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedRetrofit
import com.nunez.libellis.views.UpdateProgressView
import io.reactivex.schedulers.Schedulers

/**
 * Created by paulnunez on 10/26/17.
 */
class UpdateProgressJobService : JobService() {

    private lateinit var update: UpdateProgress
    private lateinit var goodreadsService: GoodreadsService
    private lateinit var jobParams: JobParameters
    private lateinit var receiver: UpdateStatusReceiver

    companion object {
        const val TAG = "ProgressJobService"
    }

    override fun onStartJob(params: JobParameters?): Boolean {
            Log.d(TAG, "on start Job")
            Log.d(TAG, "${params?.jobId}")
        if (params != null) {
            this.jobParams = params
            update = UpdateProgress.fromPersistableBundle(params.extras)
        }
        goodreadsService = SignedRetrofit(this)
                .instance.create(GoodreadsService::class.java)
        setUpBroadcastReceiverToStopThisJobWhenUpdateFinish()
        sendUpdateStatusStartBroadcast()
        runCorrectRequest()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        unRegisterReceiver()
        return true
    }

    private fun runCorrectRequest() {
        when {
            update.isFinished -> bookFinished(update)
            update.type == UpdateProgressView.TYPE_PAGE -> updateBookWithPages(update)
            update.type == UpdateProgressView.TYPE_PERCENT -> updateBookWithPercent(update)
            else -> {
                unRegisterReceiver()
                throw IllegalArgumentException()
            }
        }
    }

    private fun bookFinished(update: UpdateProgress) {
        goodreadsService.sendBookFinished(
                update.id,
                update.comment,
                update.rating)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { sendUpdateCompletedBroadcast() },
                        { sendUpdateErrorBroadcast() })
    }

    private fun updateBookWithPages(update: UpdateProgress) {
        goodreadsService.sendBookUpdate(
                id = update.id,
                page = update.value,
                comment = update.comment)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { sendUpdateCompletedBroadcast() },
                        { sendUpdateErrorBroadcast() })
    }

    private fun updateBookWithPercent(update: UpdateProgress) {
        goodreadsService.sendBookUpdate(
                id = update.id,
                percent = update.value,
                comment = update.comment)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { sendUpdateCompletedBroadcast() },
                        { sendUpdateErrorBroadcast() })
    }

    private fun setUpBroadcastReceiverToStopThisJobWhenUpdateFinish() {
        // The filter's action is BROADCAST_ACTION
        val statusIntentFilter = IntentFilter(UpdateStatusReceiver.UPDATE_STATUS_ACTION)

        receiver = UpdateStatusReceiver({ status ->
            when (status) {
                is UpdateStatusReceiver.Status.Started ->{
                }
                is UpdateStatusReceiver.Status.Completed -> {
                    unRegisterReceiver()
                    jobFinished(jobParams, false)
                }
                else -> {
                    unRegisterReceiver()
                    jobFinished(jobParams, true)
                }
            }
        })

        // Registers the receiver and its intent filters
        registerReceiver(
                receiver,
                statusIntentFilter)
    }

    private fun sendUpdateStatusStartBroadcast() {
        sendStatusBroadcast(UpdateStatusReceiver.EXTRA_STATUS_STARTED, "on_started")
    }

    private fun sendUpdateErrorBroadcast() {
        sendStatusBroadcast(UpdateStatusReceiver.EXTRA_STATUS_ERROR, "on_error")
    }

    private fun sendUpdateCompletedBroadcast() {
        sendStatusBroadcast(UpdateStatusReceiver.EXTRA_STATUS_COMPLETE, "completed")
    }

    private fun sendStatusBroadcast(intentExtraKey: String, message: String) {
        val intent = Intent(UpdateStatusReceiver.UPDATE_STATUS_ACTION)
        intent.putExtra(intentExtraKey, message)
        sendBroadcast(intent)
    }

    private fun unRegisterReceiver() {
        unregisterReceiver(receiver)
    }
}