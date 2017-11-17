package com.nunez.libellis.entities.raw

import com.nunez.libellis.entities.ReadStatusUpdate.Review

/**
 * Created by paulnunez on 4/30/17
 */
data class ReadStatusUpdate(
        var id: String = "",
        var status: String = "",
        var user: User,
        var review: Review = Review(),
        var updatedAt: String = ""

): Update(TYPE.READ_STATUS, user, updatedAt){
    data class Review(
            var id: String = "",
            var rating: String ="",
            var review: String = "", // Can be empty
            var commentsCount: String = "",
            var spoiler: String = "",
            var book: Book = Book()
    )
}