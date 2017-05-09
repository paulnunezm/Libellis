package com.nunez.libellis.repository.parsers

import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpression
import javax.xml.xpath.XPathFactory

open class BaseParserTest {
    val factory by lazy { DocumentBuilderFactory.newInstance() }
    val builder by lazy { factory.newDocumentBuilder() }
    val xPathFactory by lazy { XPathFactory.newInstance() }
    val xpath by lazy { xPathFactory.newXPath() }

    lateinit var xPathExpression: XPathExpression

    fun getFileFromPath(obj: Any, fileName: String): File {
        val classLoader = obj.javaClass.classLoader
        val resource = classLoader.getResource(fileName)
        return File(resource.path)
    }

    fun getNode(response: String, nodePath: String): Node {
        val doc = InputSource(StringReader(response))
        xPathExpression = xpath.compile(nodePath)
        val result = xPathExpression.evaluate(doc, XPathConstants.NODESET)
        val nodes = result as NodeList

        return nodes.item(0)
    }

}