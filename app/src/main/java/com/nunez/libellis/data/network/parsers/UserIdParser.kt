package com.nunez.libellis.data.network.parsers

import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.xpath.XPathConstants

/**
 * Handles the parse for the /api/auth_user endpoint response
 */
class UserIdParser(val response: String) : BaseParser() {

    fun parse():String{
        var userId = ""

        try {
            val doc = InputSource(StringReader(response))
            xPathExpression = xpath.compile("//user")

            val result = xPathExpression.evaluate(doc, XPathConstants.NODESET)
            val nodes = result as NodeList
            if(nodes.length == 0) throw UnsupportedOperationException("Can't parse document without nodes")

            userId = nodes.item(0).attributes.item(0).nodeValue

        }catch (e: Exception){

        }

        return userId
    }
}