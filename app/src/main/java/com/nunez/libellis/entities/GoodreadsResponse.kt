package com.nunez.libellis.entities

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * Created by paulnunez on 4/25/17.
 */

@Root(strict = false)
class GoodreadsResponse(
        @field:ElementList(name = "updates") var updates: ArrayList<Update>? = null


        /** For better understanding about why the @field go to https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets */
)