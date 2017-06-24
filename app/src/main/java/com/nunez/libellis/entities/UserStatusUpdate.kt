package com.nunez.libellis.entities

/**
 * Created by paulnunez on 5/1/17
 */
data class UserStatusUpdate(
        var user: User = User(),
        var status: Status = UserStatusUpdate.Status(),
        var updateAt: String = ""

): Update(Update.TYPE.USER_STATUS, user, updateAt){

    data class Status(
            var id: String = "",
            var page: String = "",
            var percent: String = "",
            var commentsCount: String = "",
            var reviewId: String = "",
            var book: Book = Book()
    )
}