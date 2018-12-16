package com.nunez.libellis.domain.entities.raw

/**
 * Created by paulnunez on 4/29/17.
 */
data class CommentUpdate (
        val user: User,
        val status: String,
        val comment: String,
        val reviewLink: String,
        val updatedAt: String

): Update(TYPE.COMMENT, user, updatedAt)