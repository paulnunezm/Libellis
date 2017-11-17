package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.raw.ReadStatusUpdate
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Node
import java.io.File

/**
 * Created by paulnunez on 5/1/17
 */
class ReadStatusUpdateParserTest : BaseParserTest() {

    // given setup
    lateinit var file: File
    lateinit var node: Node

    // when setup
    lateinit var readstatus: ReadStatusUpdate

    @Before
    fun setUp() {
        file = getFileFromPath(this, "update_readstatus.xml")
        node = getNode(file.readText(), "//updates/update")
        readstatus = ReadStatusUpdateParser(node).parse()
    }

    @Test
    fun shouldParseReadStatusFields() {
        assertEquals("id", "1815838630", readstatus.id)
        assertEquals("id", "currently-reading", readstatus.status)
    }

    @Test
    fun shouldParseActor() {
        // then
        assertEquals("31273409", readstatus.user.id)
        assertEquals("Elianna", readstatus.user.name)
    }

    @Test
    fun shouldParseUpdatedAt() {
        assertEquals("Updated time", "Tue, 25 Apr 2017 12:52:20 -0700", readstatus.updatedAt)
    }

    @Test
    fun shouldParseReview() {
        assertEquals("review id", "1982119542", readstatus.review.id)
        assertEquals("review", "", readstatus.review.review)
        assertEquals("review comments count", "0", readstatus.review.commentsCount)
        assertEquals("Book id", "22469811", readstatus.review.book.id)
        assertEquals("Book title","The Arrangement 15: The Ferro Family (The Arrangement, #15)",
                readstatus.review.book.title)
        assertEquals("book author", "4330590", readstatus.review.book.authors[0].id)
        assertEquals("book author", "H.M. Ward", readstatus.review.book.authors[0].name)

    }
}