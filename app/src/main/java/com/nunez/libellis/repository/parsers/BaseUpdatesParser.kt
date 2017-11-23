package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.raw.AuthorRaw
import com.nunez.libellis.entities.raw.BookRaw
import com.nunez.libellis.entities.raw.User
import org.w3c.dom.Node
import org.w3c.dom.NodeList

/**
 * Created by paulnunez on 4/29/17.
 */
open class BaseUpdatesParser : BaseParser() {

    fun parseActor(actorNode: NodeList): User {
        val user = User()

        iterateInChildNotes(actorNode, { actorNodeChild ->
            when (actorNodeChild.nodeName) {
                "id" -> user.id = getNodeValue(actorNodeChild)
                "name" -> user.name = getNodeValue(actorNodeChild)
                "image_url" -> user.imageUrl = getNodeValue(actorNodeChild)
                "link" -> user.profileUrl = getNodeValue(actorNodeChild)
            }
        })

        return user
    }

    fun parseBook(bookNode: Node): BookRaw {
        var book = BookRaw()

        iterateInChildNotes(bookNode.childNodes, {
            when (it.nodeName) {
                "id" -> book.id = getNodeValue(it)
                "title" -> {
                    val title = parseDataSection(it)

                    // Little hack. Becasuse sometimes the book titles doesn't come with a data
                    //section. This is due by the unorganized responses
                    book.title = if (title.isNotEmpty()) title else getNodeValue(it)

                }
                "authors" -> {
                    val tempBook = parseAuthors(it.childNodes)
                    with(tempBook) {
                        book.authors = authors
                        book.averageRating = averageRating
                        book.ratingsCount = ratingsCount
                    }
                }
                "author" -> {
                    var authors = ArrayList<AuthorRaw>()
                    val tempBook = BookRaw()
                    authors.add(parseAuthor(it, tempBook))

                    with(tempBook) {
                        book.authors = authors
                        book.averageRating = averageRating
                        book.ratingsCount = ratingsCount
                    }
                }
            }
        })

        return book
    }

    fun parseDataSection(dataSectionNode: Node): String {
        var text = ""
        iterateInChildNotes(dataSectionNode.childNodes, {
            val nodeName = it.nodeName
            if (nodeName.contains("data-section") || nodeName.contains("text")) {
                text = it.textContent
            }
        })
        return text
    }

    private fun parseAuthors(authorsNode: NodeList): BookRaw {
        var book = BookRaw()
        var authors = ArrayList<AuthorRaw>()

        iterateInChildNotes(authorsNode, {
            if (it.nodeName == "author") {
                val author = parseAuthor(it, book)
                authors.add(author)
            }
        })

        book.authors = authors
        return book
    }

    private fun parseAuthor(node: Node, book: BookRaw): AuthorRaw {
        val author = AuthorRaw()
        iterateInChildNotes(node.childNodes, {
            when (it.nodeName) {
                "id" -> author.id = getNodeValue(it)
                "name" -> author.name = getNodeValue(it)
                "image_url" -> author.imageUrl = parseDataSection(it)
                "average_rating" -> book.averageRating = getNodeValue(it)
                "ratings_count" -> book.ratingsCount = getNodeValue(it)
            }
        })
        return author
    }
}