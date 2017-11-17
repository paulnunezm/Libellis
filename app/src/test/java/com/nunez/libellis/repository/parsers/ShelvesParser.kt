package com.nunez.libellis.repository.parsers

import com.nunez.libellis.entities.raw.GoodreadsResponse
import junit.framework.Assert
import org.junit.Test
import org.simpleframework.xml.core.Persister

/**
 * Created by paulnunez on 5/18/17.
 */
class ShelvesParser: BaseParserTest() {

    private fun getResponse(): GoodreadsResponse? {
        val file = getFileFromPath(this, "shelves.xml")

        // when
        val serializer = Persister()
        val response = serializer.read(GoodreadsResponse::class.java, file)
        return response
    }

    @Test
    fun shouldParseShelvesResponse(){
        // when
        val response = getResponse()

        // then
        Assert.assertNotNull(response)
    }

    @Test
    fun shouldReturnCorrectShelve(){
        // given
        val response = getResponse()

        // then
        val shelveName = response?.shelves?.get(0)?.name

        Assert.assertEquals("Shelve Name","read", shelveName)
    }

    @Test
    fun shouldReturnCorrectNumberOfShelves(){
        val shelvesCount = getResponse()?.shelves?.size

        Assert.assertEquals("number of shelves",3,shelvesCount)
    }


}