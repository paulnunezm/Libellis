package com.nunez.libellis.repository.parsers

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by paulnunez on 4/30/17
 */
class ReviewsUpdateParserTest : BaseParserTest() {

    @Test
    fun shouldParseReviewsNode() {
        //given
        val file = getFileFromPath(this, "update_review.xml")
        val node = getNode(file.readText(), "//updates/update")

        // when
        val review = ReviewsUpdateParser(node).parse()

        // then
        with(review) {
            assertEquals("user id", "22360802", user.id)
            assertEquals("user image", "https://images.gr-assets.com/users/1415927931p2/22360802.jpg",
                    user.imageUrl)
            assertEquals("updateTIme", "Tue, 25 Apr 2017 04:43:27 -0700", updatedAt)
            assertEquals("rating", "3", rating)
            assertEquals("book id", "1", book.id)
            assertEquals("book title", "Harry Potter and the Half-Blood Prince (Harry Potter, #6)",
                    book.title)
            assertEquals("aurhor name","J.K. Rowling", book.authors[0].name )
        }

    }
}