package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.raw.BookRaw
import com.nunez.libellis.entities.raw.ReviewUpdate
import com.nunez.libellis.entities.raw.User
import org.w3c.dom.Node

/**
 * Created by paulnunez on 4/29/17
 */
class ReviewsUpdateParser(val reviewNode: Node) : BaseUpdatesParser() {

    fun parse(): ReviewUpdate {

        var user = User()
        var reviewLink = ""
        var bookImageUrl = ""
        var updatedAt = ""
        var rating = ""
        var book = BookRaw()

        iterateInChildNotes(reviewNode.childNodes, {
            when (it.nodeName) {
                "actor" -> user = parseActor(it.childNodes)
                "link" -> reviewLink = getNodeValue(it)
                "image_url" -> bookImageUrl = getNodeValue(it)
                "updated_at" -> updatedAt = getNodeValue(it)
                "action" -> {
                    iterateInChildNotes(it.childNodes, {
                        if (it.nodeName == "rating") rating = getNodeValue(it)
                    })
                }
                "object" -> {
                    iterateInChildNotes(it.childNodes, {
                        if (it.nodeName == "book") book = parseBook(it)
                    })
                }
            }
        })

        return ReviewUpdate(user, reviewLink, bookImageUrl, updatedAt, rating, book)
    }
}