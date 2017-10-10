package com.nunez.libellis.entities

/**
 * Created by paulnunez on 10/10/17.
 */
data class UpdateProgress(
        val id: Int,
        val type: String,
        val comment: String = "",
        val isFinished: Boolean = true,
        val rating: Int = 0
)