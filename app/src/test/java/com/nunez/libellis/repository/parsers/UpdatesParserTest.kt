package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.FriendUpdate
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
    fun shouldHandleEmptyFileAndReturnEmptyList() {
        // when
        updates = UpdatesParser("").parse()

        // then
        assertTrue("Updates is returned empty", updates.isEmpty())
    }

    @Test
    fun shouldHandleEmptyUpdates(){
        // given
        response = getFileFromPath(this, "update_empty.xml")

        // when
        parser = UpdatesParser(response.readText())
        updates = parser.parse()
    }

}