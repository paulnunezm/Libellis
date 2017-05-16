package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.CommentUpdate
import com.nunez.libellis.entities.User
import org.w3c.dom.Node

/**
 * Created by paulnunez on 4/29/17
 */
class CommentUpdaterParser(val commentNode: Node) : BaseUpdatesParser() {

    fun parse(): CommentUpdate {
        var user = User()
        var status = ""
        var comment = ""
        var reviewLink = ""
        var updatedAt = ""

        iterateInChildNotes(commentNode.childNodes,
                { commentNode ->
                    when(commentNode.nodeName){
                        "action_text"-> {
                            status = parseDataSection(commentNode)
                            status = status.substring(status.indexOf("commented"))
                        }
                        "link" -> reviewLink = getNodeValue(commentNode)
                        "body" -> comment = getNodeValue(commentNode)
                        "actor" -> user = parseActor(commentNode.childNodes)
                        "updated_at" -> updatedAt = getNodeValue(commentNode)
                    }
                })
        return CommentUpdate(user, status, comment, reviewLink, updatedAt)
    }
}