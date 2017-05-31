package com.nunez.libellis.repository.parsers

import junit.framework.Assert
import org.junit.Test


class UserIdParserTest : BaseParserTest() {


    @Test
    fun parse() {
        val file = getFileFromPath(this, "user_id.xml")
        val userId = UserIdParser(file.readText()).parse()

        Assert.assertEquals("User id", "28834352", userId)
    }

}