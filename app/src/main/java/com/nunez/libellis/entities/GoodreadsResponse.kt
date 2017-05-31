package com.nunez.libellis.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * Created by paulnunez on 4/25/17.
 */

@Root(strict = false)
class GoodreadsResponse(
        /** note: simpleXml needs consctructor without argumentes, hence the null initialization
         * for each field.*/
        @field:ElementList(name = "updates", required = false) var updates: ArrayList<Update>? = null,
        @field:ElementList(name = "shelves", required = false) var shelves: List<Shelve>? = null,
        @field:Element(name = "user", required = false) var user: GoodreadsUserResponse? = null

        /** For better understanding about why the @field go to https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets */
)

