package com.nunez.libellis.entities

/**
 * Created by paulnunez on 4/29/17.
 */
data class FriendUpdate(
        val type: String,
        val user: User,
        val updatedAt: String,
        val friendName: String,
        val friendImageUrl: String,
        val friendProfileUrl: String

) : Update(type, user, updatedAt)