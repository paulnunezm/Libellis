package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.ReadStatusUpdate
import com.nunez.libellis.entities.User
import org.w3c.dom.Node

/**
 * Created by paulnunez on 4/30/17
 */
class ReadStatusUpdateParser(val readStatusNode: Node) : BaseUpdatesParser() {

    fun parse(): ReadStatusUpdate {
        var id = ""
        var status = ""
        var user = User()
        var updatedAt = ""
        var review = ReadStatusUpdate.Review()

        iterateInChildNotes(readStatusNode.childNodes, {
            when (it.nodeName) {
                "actor" -> user = parseActor(it.childNodes)
                "updated_at" -> updatedAt = getNodeValue(it)
                "object" -> {
                    iterateInChildNotes(it.childNodes, {
                        if (it.nodeName == "read_status") {
                            iterateInChildNotes(it.childNodes, {
                                when (it.nodeName) {
                                    "id" -> id = getNodeValue(it)
                                    "review_id" -> review.id = getNodeValue(it)
                                    "status" -> status = getNodeValue(it)
                                    "review" -> {
                                        val tempId = review.id
                                        review = parseReview(it)
                                        review.id = tempId
                                    }
                                }
                            })
                        }
                    })
                }

            }
        })


        return ReadStatusUpdate(id, status, user, review, updatedAt)
    }

    private fun parseReview(node: Node): ReadStatusUpdate.Review {
        var review = ReadStatusUpdate.Review()

        iterateInChildNotes(node.childNodes, {
            when (it.nodeName) {
                "review" -> review.review = getNodeValue(it)
                "comments_count" -> review.commentsCount = getNodeValue(it)
                "spoiler_flag" -> review.spoiler = getNodeValue(it)
                "book" ->{
                    review.book = parseBook(it)
                }
            }
        })
        return review
    }
}