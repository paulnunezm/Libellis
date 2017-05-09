package com.nunez.libellis.entities

/**
 * Created by paulnunez on 4/29/17
 */
data class ReviewUpdate(
        val user: User,
        val reviewLink: String,
        val bookImageUrl: String,
        val updatedAt: String,
        val rating: String,
        val book: Book

) : Update(Update.TYPE.REVIEW, user, updatedAt)