package com.nunez.libellis.entities

import android.os.PersistableBundle
import com.nunez.libellis.views.UpdateProgressView

data class UpdateProgress(
        val id: String = "",
        val type: Int = UpdateProgressView.TYPE_PERCENT,
        val value: String = "",
        val comment: String = "",
        val isFinished: Boolean = true,
        val rating: Int = 0) {

    companion object {
        private const val BUNDLE_KEY_ID = "id"
        private const val BUNDLE_KEY_TYPE = "type"
        private const val BUNDLE_KEY_VALUE = "value"
        private const val BUNDLE_KEY_COMMENT = "comment"
        private const val BUNDLE_KEY_IS_FINISHED = "isFinished"
        private const val BUNDLE_KEY_RATING = "rating"

        /**
         * Persistable bundle is used to be able to send the object to the jobService
         * that handles the update reading status @link{UpdateProgressJobService}.
         *
         * We use persistableBundle because it is the implementation used by the jobservice
         * to send extras.
         */
        fun fromPersistableBundle(bundle: PersistableBundle): UpdateProgress {
            with(bundle) {
                val finished = getInt(BUNDLE_KEY_IS_FINISHED) == 1 // getBoolean is only for api 22+
                return UpdateProgress(
                        getString(BUNDLE_KEY_ID),
                        getInt(BUNDLE_KEY_TYPE),
                        getString(BUNDLE_KEY_VALUE),
                        getString(BUNDLE_KEY_COMMENT),
                        finished,
                        getInt(BUNDLE_KEY_RATING)
                )
            }

        }
    }

    fun toPersistableBundle(): PersistableBundle {
        // because putBoolean is only for api 22+
        val finished = if (isFinished) 1 else 0

        val bundle = PersistableBundle()
        bundle.apply {
            putString(BUNDLE_KEY_ID, id)
            putInt(BUNDLE_KEY_TYPE, type)
            putString(BUNDLE_KEY_VALUE, value)
            putString(BUNDLE_KEY_COMMENT, comment)
            putInt(BUNDLE_KEY_IS_FINISHED, finished)
            putInt(BUNDLE_KEY_RATING, rating)
        }
        return bundle
    }

}