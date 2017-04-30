package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.Book
import com.nunez.libellis.entities.ReviewUpdate
import com.nunez.libellis.entities.User
import org.w3c.dom.Node

/**
 * Created by paulnunez on 4/29/17.
 */
class ReviewsUpdateParser(val reviewNode: Node) : BaseUpdatesParser() {

    fun parse(): ReviewUpdate {

        var user = User()
        var reviewLink = ""
        var bookImageUrl = ""
        var updatedAt = ""
        var rating = ""
        var book = Book()

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