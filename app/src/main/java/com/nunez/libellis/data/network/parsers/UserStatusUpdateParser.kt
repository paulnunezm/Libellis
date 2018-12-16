package com.nunez.libellis.data.network.parsers

import com.nunez.libellis.domain.entities.raw.User
import com.nunez.libellis.domain.entities.raw.UserStatusUpdate
import org.w3c.dom.Node

/**
 * Created by paulnunez on 5/1/17.
 */
class UserStatusUpdateParser(val statusNode: Node) : BaseUpdatesParser() {

    fun parse(): UserStatusUpdate {
        var user = User()
        var status = UserStatusUpdate.Status()
        var updatedAt = ""

        iterateInChildNotes(statusNode.childNodes, {
            when (it.nodeName) {
                "actor" -> user = parseActor(it.childNodes)
                "updated_at" -> updatedAt = getNodeValue(it)
                "object" -> {
                    iterateInChildNotes(it.childNodes, {
                        if (it.nodeName == "user_status") {
                            iterateInChildNotes(it.childNodes, {
                                when (it.nodeName) {
                                    "id" -> status.id = getNodeValue(it)
                                    "book_id" -> status.book.id = getNodeValue(it)
                                    "page" -> status.page = getNodeValue(it)
                                    "percent" -> status.percent = getNodeValue(it)
                                    "comments_count" -> status.commentsCount = getNodeValue(it)
                                    "review_id" -> status.reviewId = getNodeValue(it)
                                    "book" -> status.book = parseBook(it)
                                }
                            })
                        }
                    })
                }
            }
        })

        return UserStatusUpdate(user, status, updatedAt)
    }

}