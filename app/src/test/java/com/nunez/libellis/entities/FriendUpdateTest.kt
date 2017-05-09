package com.nunez.libellis.entities

import com.nunez.libellis.repository.parsers.BaseParserTest
import com.nunez.libellis.repository.parsers.FriendUpdateParser
import org.junit.Assert
import org.junit.Test

class FriendUpdateTest : BaseParserTest() {


    @Test
    fun shouldParseFriendNode() {
        // given
        val file = getFileFromPath(this, "update_friend.xml")
        val node = getNode(file.readText(), "//updates/update")

        // when
        var friendUpdate = FriendUpdateParser(node).parse()

        // then
        Assert.assertEquals("Nelson Nuñez", friendUpdate.user.name)
    }
}