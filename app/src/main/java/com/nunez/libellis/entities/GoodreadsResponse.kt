package com.nunez.libellis.entities

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false)
class GoodreadsResponse(
        /** note: simpleXml needs a constructor without arguments, hence the null initialization
         * for each field.*/
        @field:ElementList(name = "updates", required = false) var updates: ArrayList<Update>? = null,
        @field:ElementList(name = "shelves", required = false) var shelves: List<Shelve>? = null,
        @field:ElementList(name = "reviews", required = false) var reviews: List<Review>? = null,
        @field:Element(name = "review", required = false) var review: Review? = null

        /** For better understanding about why the @field go to https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets */
)

