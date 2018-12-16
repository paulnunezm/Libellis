package com.nunez.libellis.domain.entities.raw

/**
 * Created by paulnunez on 5/1/17
 */
data class UserStatusUpdate(
        var user: User = User(),
        var status: Status = Status(),
        var updateAt: String = ""

): Update(TYPE.USER_STATUS, user, updateAt){

    data class Status(
            var id: String = "",
            var page: String = "",
            var percent: String = "",
            var commentsCount: String = "",
            var reviewId: String = "",
            var book: BookRaw = BookRaw()
    )
}