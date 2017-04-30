package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.Author
import com.nunez.libellis.entities.Book
import com.nunez.libellis.entities.User
import org.w3c.dom.Node
import org.w3c.dom.NodeList

/**
 * Created by paulnunez on 4/29/17.
 */
open class BaseUpdatesParser {

    fun getNodeValue(node: Node): String {
        return node.childNodes.item(0).nodeValue
    }


    /**
     * Iterates ove a childList and return a node. This is for not repeat
     * this lines everytime we're trying to parse a response.
     */
    fun iterateInChildNotes(nodeList: NodeList, f: (node: Node) -> Unit) {
        for (i in 0..nodeList.length - 1) {
            f(nodeList.item(i))
        }
    }

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

    fun parseBook(bookNode: Node): Book {
        var book = Book()

        iterateInChildNotes(bookNode.childNodes, {
            when (it.nodeName) {
                "id" -> book.id = getNodeValue(it)
                "title" -> book.title = parseDataSection(it)
                "authors" -> {
                    val tempBook = parseAuthors(it.childNodes)
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

    private fun parseDataSection(dataSectionNode: Node): String {
        var text = ""
        iterateInChildNotes(dataSectionNode.childNodes, {
            if (it.nodeName.contains("data-section")) {
                text = it.textContent
            }
        })
        return text
    }

    private fun parseAuthors(authorsNode: NodeList): Book {
        var book = Book()
        var authors = ArrayList<Author>()

        iterateInChildNotes(authorsNode, {
            if (it.nodeName == "author") {
                val author = Author()
                iterateInChildNotes(it.childNodes, {
                    when (it.nodeName) {
                        "id" -> author.id = getNodeValue(it)
                        "name" -> author.name = getNodeValue(it)
                        "image_url" -> author.imageUrl = parseDataSection(it)
                        "average_rating" -> book.averageRating = getNodeValue(it)
                        "ratings_count" -> book.ratingsCount = getNodeValue(it)
                    }
                })
                authors.add(author)
            }
        })

        book.authors = authors
        return book
    }
}