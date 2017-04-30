package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.CommentUpdate
import com.nunez.libellis.entities.FriendUpdate
import com.nunez.libellis.entities.ReviewUpdate
import com.nunez.libellis.entities.Update
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class UpdatesParserTest {

    lateinit var response: File
    lateinit var updates: ArrayList<Update>
    lateinit var parser: UpdatesParser


    private fun getFileFromPath(obj: Any, fileName: String): File {
        val classLoader = obj.javaClass.classLoader
        val resource = classLoader.getResource(fileName)
        return File(resource.path)
    }

    @Test
    fun shouldParseFriendUpdate() {
        // given
        response = getFileFromPath(this, "update_friend.xml")

        // when
        parser = UpdatesParser(response.readText())
        updates = parser.parse()

        // Then
        assertEquals("updates size", 1, updates.size)
        assertTrue("Update is a Friend Update", updates.get(0) is FriendUpdate)
    }

    @Test
    @Throws (Exception::class)
    fun shouldHandleEmptyFileAndReturnEmptyList() {
        // when
        updates = UpdatesParser("").parse()

        // then
        assertTrue("Updates is returned empty", updates.isEmpty())
    }

    @Test
    @Throws (Exception::class)
    fun shouldHandleEmptyUpdates(){
        // given
        response = getFileFromPath(this, "update_empty.xml")

        // when
        parser = UpdatesParser(response.readText())
        updates = parser.parse()
    }

    @Test
    fun shouldParseCommentUpdate() {
        // given
        response = getFileFromPath(this, "update_comment.xml")

        // when
        parser = UpdatesParser(response.readText())
        updates = parser.parse()

        // Then
        assertEquals("updates size", 1, updates.size)
        assertTrue("Update is a Comment Update", updates.get(0) is CommentUpdate)
    }

    @Test
    fun shouldParseReviewUpdate() {
        // given
        response = getFileFromPath(this, "update_review.xml")

        // when
        parser = UpdatesParser(response.readText())
        updates = parser.parse()

        // Then
        assertEquals("updates size", 1, updates.size)
        assertTrue("Update is a Review Update", updates.get(0) is ReviewUpdate)
    }

}