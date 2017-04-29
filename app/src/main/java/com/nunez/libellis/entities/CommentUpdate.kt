package com.nunez.libellis.entities

/**
 * Created by paulnunez on 4/29/17.
 */
data class CommentUpdate (
        val user: User ,
        val comment: String,
        val reviewLink: String,
        val updatedAt: String

): Update(Update.TYPE.COMMENT, user, updatedAt)