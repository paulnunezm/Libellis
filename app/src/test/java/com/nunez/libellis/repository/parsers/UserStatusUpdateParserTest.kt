package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.UserStatusUpdate
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.w3c.dom.Node
import java.io.File

/**
 * Created by paulnunez on 5/1/17.
 */
class UserStatusUpdateParserTest : BaseParserTest() {

    // given setup
    lateinit var file: File
    lateinit var node: Node

    // when setup
    lateinit var userStatus: UserStatusUpdate

    @Before
    fun setUp() {
        file = getFileFromPath(this, "update_userstatus.xml")
        node = getNode(file.readText(), "//updates/update")
        userStatus = UserStatusUpdateParser(node).parse()
    }


    @Test
    fun shouldParseActor() {
        // then
        assertEquals("31273409", userStatus.user.id)
        assertEquals("Elianna", userStatus.user.name)
    }

    @Test
    fun shouldParseUpdatedAt() {
        assertEquals("Updated time", "Tue, 25 Apr 2017 05:00:55 -0700", userStatus.updateAt)
    }

    @Test
    fun shouldParseUserStatus() {
        with(userStatus.status) {
            assertEquals("id", "129627581", id)
            assertEquals("page", "228", page)
            assertEquals("comments count", "0", commentsCount)
            assertEquals("review id", "1980787207", reviewId)
            assertEquals("book title", "En Nombre del Amor", book.title)
        }
    }

}