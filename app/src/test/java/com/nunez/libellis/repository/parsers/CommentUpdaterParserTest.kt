package com.nunez.libellis.repository.parsers

import org.junit.Assert
import org.junit.Test

class CommentUpdaterParserTest: BaseParserTest(){

    @Test
    fun shouldPassCommentNode(){
        // given
        val file = getFileFromPath(this, "update_comment.xml")
        val node = getNode(file.readText(), "//updates/update")

        // when
        var commentUpdate = CommentUpdaterParser(node).parse()

        // then
        Assert.assertEquals("22360802", commentUpdate.user.id)
    }
}