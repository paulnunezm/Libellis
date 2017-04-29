package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.User
import org.w3c.dom.Node
import org.w3c.dom.NodeList

/**
 * Created by paulnunez on 4/29/17.
 */
open class BaseUpdatesParser {

    fun getNodeValue(node: Node): String{
        return node.childNodes.item(0).nodeValue
    }

    fun parseActor(actorNode: NodeList): User {
        val user = User()

        for(i in 0..actorNode.length-1){
            val actorChildrenNode = actorNode.item(i)

            when(actorChildrenNode.nodeName){
                "id" -> user.id = getNodeValue(actorChildrenNode)
                "name" -> user.name = getNodeValue(actorChildrenNode)
                "image_url" -> user.imageUrl = getNodeValue(actorChildrenNode)
                "link" -> user.profileUrl = getNodeValue(actorChildrenNode)
            }
        }

        return user
    }
}