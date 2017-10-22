package com.nunez.libellis.entities

import com.nunez.libellis.views.UpdateProgressView

data class UpdateProgress(
        val id: String = "",
        val type: Int = UpdateProgressView.TYPE_PERCENT,
        val value: String = "",
        val comment: String = "",
        val isFinished: Boolean = true,
        val rating: Int = 0
)