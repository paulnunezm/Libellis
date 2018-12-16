package com.nunez.libellis.ui.screens.main.reading.updateProgress.jobServices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by paulnunez on 10/26/17.
 */
class UpdateStatusReceiver(
        val listener: (Status) -> Unit
) : BroadcastReceiver() {

    companion object {
        const val UPDATE_STATUS_ACTION = "com.nunez.libellis.PROGRESS_UPDATE"
        const val EXTRA_STATUS_STARTED = "started"
        const val EXTRA_STATUS_COMPLETE = "complete"
        const val EXTRA_STATUS_ERROR = "error"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val extra = intent?.extras
        extra?.let {
            when {
                it.containsKey(EXTRA_STATUS_STARTED) -> listener(Status.Started())
                it.containsKey(EXTRA_STATUS_COMPLETE) -> listener(Status.Completed())
                else -> listener(Status.Error())
            }
        }
    }

    sealed class Status {
        class Started : Status()
        class Completed : Status()
        class Error : Status()
    }
}