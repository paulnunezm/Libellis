package com.nunez.libellis.repository

import com.nunez.libellis.entities.GoodreadsResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.simpleframework.xml.core.Persister
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpression
import javax.xml.xpath.XPathFactory

class RequestParsingTest {

    val factory by lazy { DocumentBuilderFactory.newInstance() }
    val builder by lazy { factory.newDocumentBuilder() }
    val xPathFactory by lazy { XPathFactory.newInstance() }
    val xpath by lazy { xPathFactory.newXPath() }

    lateinit var xPathExpression: XPathExpression

    lateinit var persister: Persister

    @Before
    fun setUp() {
        persister = Persister()
        factory.isNamespaceAware = true
    }

    private fun getFileFromPath(obj: Any, fileName: String): File {
        val classLoader = obj.javaClass.classLoader
        val resource = classLoader.getResource(fileName)
        return File(resource.path)
    }

    private fun getResponse(responseXmlPath: String): GoodreadsResponse {
        val file = getFileFromPath(this, responseXmlPath)
        assertNotNull(file)
        return persister.read(GoodreadsResponse::class.java, file)
    }

    @Test
    @Throws(Exception::class)
    fun shouldPassNoUpdatesResponse() {
        // given
        val response = getResponse("update_empty.xml")

        // when
        val updates = response.updates

        // then
        assertNotNull(updates)
    }


    @Test
    @Throws(Exception::class)
    fun shouldPassWithFriendCommentTypeWithXpath() {
        // given
        val response = getFileFromPath(this, "update_friend.xml")

        // when
        val doc = builder.parse(response)
        xPathExpression = xpath.compile("//updates/update[@type='friend']/actor/name/text()")
        val result = xPathExpression.evaluate(doc, XPathConstants.NODESET)
        val nodes = result as NodeList
        val name: String? = nodes.item(0).nodeValue

        // then
        assertEquals("Equal user name", "Nelson Nuñez", name)
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetResponsesObjects() {
        // given
        val response = getFileFromPath(this, "updates.xml")

        // when
        val doc = builder.parse(response)
        xPathExpression = xpath.compile("//updates/update")
        val result = xPathExpression.evaluate(doc, XPathConstants.NODESET)
        val nodes = result as NodeList

        // Then
        assertEquals(11, nodes.length)

        for (i in 0..nodes.length - 1) {
            System.out.println(nodes.item(i).attributes.item(0).toString())
        }
    }
}