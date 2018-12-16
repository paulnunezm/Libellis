package com.nunez.libellis.data.network.parsers

import com.nunez.libellis.domain.entities.raw.FriendUpdate
import com.nunez.libellis.domain.entities.raw.Update
import com.nunez.libellis.domain.entities.raw.User
import org.w3c.dom.Node

/**
 * Created by paulnunez on 4/29/17
 */
open class FriendUpdateParser(val friendNode: Node) : BaseUpdatesParser() {

    fun parse(): FriendUpdate {

        var friendName = ""
        var friendProfileUrl = ""
        var friendImageUrl = ""
        var updatedAt = ""
        var user = User()

        for (i in 0..friendNode.childNodes.length - 1) {
            val currentNode = friendNode.childNodes.item(i)

            when (currentNode.nodeName) {
                "action_text" ->{
                    friendName = parseDataSection(currentNode)
                    friendName = friendName.substring(friendName.lastIndexOf("with"))
                            .replace("with", "")
                }
                "link" -> friendProfileUrl = getNodeValue(currentNode)
                "image_url" -> friendImageUrl = getNodeValue(currentNode)
                "updated_at" -> updatedAt = getNodeValue(currentNode)
                "actor" -> user = parseActor(currentNode.childNodes)
            }
        }

        return FriendUpdate(Update.TYPE.FRIEND, user, updatedAt, friendName, friendImageUrl, friendProfileUrl)
    }
}