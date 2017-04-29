package com.nunez.libellis.repository.parsers

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
}