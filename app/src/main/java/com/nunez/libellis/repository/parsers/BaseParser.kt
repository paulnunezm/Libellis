package com.nunez.libellis.repository.parsers

import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathExpression
import javax.xml.xpath.XPathFactory

/**
 * Created by paulnunez on 5/19/17.
 */
open class BaseParser {

    val factory by lazy { DocumentBuilderFactory.newInstance() }
    val builder by lazy { factory.newDocumentBuilder()}
    val xPathFactory by lazy { XPathFactory.newInstance() }
    val xpath by lazy { xPathFactory.newXPath() }

    lateinit var xPathExpression: XPathExpression

    fun getNodeValue(node: Node): String {
        val child : Node? = node.childNodes.item(0)
        return child?.nodeValue ?: ""
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

}