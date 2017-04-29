package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.Update
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.StringReader
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpression
import javax.xml.xpath.XPathFactory

@Suppress("IMPLICIT_CAST_TO_ANY")
/**
 * Created by paulnunez on 4/29/17.
 */
class UpdatesParser(
        val response: String) {

    private val factory by lazy { DocumentBuilderFactory.newInstance() }
    private val builder by lazy { factory.newDocumentBuilder()}
    private val xPathFactory by lazy { XPathFactory.newInstance() }
    private val xpath by lazy { xPathFactory.newXPath() }

    private lateinit var xPathExpression: XPathExpression

    var updates = ArrayList<Update>()

    fun parse(): ArrayList<Update> {

        try{
            val doc = InputSource(StringReader(response))
            xPathExpression = xpath.compile("//updates/update")

            val result = xPathExpression.evaluate(doc, XPathConstants.NODESET)
            val nodes = result as NodeList

            if(nodes.length == 0) throw UnsupportedOperationException("Can't parse document without nodes")

            for (i in 0..nodes.length - 1) {
                val node = nodes.item(i)
                val type:String = nodes.item(i).attributes.item(0).toString()

                val update = when{
                    type.contains(Update.TYPE.FRIEND) -> FriendUpdateParser(node).parse()
                    else -> false
                }

                if(update is Update) updates.add(update)

            }
        }catch (e: Exception){
            // do nothing and return an empty list.
        }

        return updates
    }

}