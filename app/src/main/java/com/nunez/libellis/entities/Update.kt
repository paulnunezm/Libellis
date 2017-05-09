package com.nunez.libellis.entities

/**
 * Created by paulnunez on 4/25/17.
 */

abstract class Update(
        val updateType: String,
        val updateUser: User,
        val updateTime: String) {

    class TYPE {
        companion object {
            val FRIEND = "friend"
            val COMMENT = "comment"
            val READ_STATUS = "readstatus"
            val USER_STATUS = "userstatus"
            val REVIEW = "review"
        }
    }
}