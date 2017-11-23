package com.nunez.libellis.entities.raw

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * This classes are meant to deal with the
 * books on shelf request.
 *
 * In the response each book is on a review node.
 */


@Root(name = "reviews", strict = false)
class Reviews(
        @field:Attribute var start:Int = 0,
        @field:Attribute var end:Int = 0,
        @field:Attribute var total:Int = 0,
        @field:ElementList(name = "review") var review:List<Review>
)


@Root(name = "review", strict = false)
class Review(
        @field:Element var id:String = "",
        @field:Element var book: ReviewBook? = null,
        @field:ElementList(name = "user_statuses", required = false) var userStatuses: List<ReviewUserStatus>? = null
)

@Root(name = "book", strict = false)
data class ReviewBook(
        @field:Element var id: String = "",
        @field:Element var title: String = "",
        @field:ElementList var authors: ArrayList<ReviewAuthor>? = null,
        @field:Element(name = "num_pages", required = false) var numberOfPages: Int = 0

)

@Root(name = "author", strict = false)
data class ReviewAuthor(
         @field:Element var id: String = "",
         @field:Element var name: String = ""
)

@Root(name= "user_status", strict = false)
data class ReviewUserStatus(
        @field:Element var id: String = "",
        @field:Element(required = false) var page:Int = 0,
        @field:Element(required = false) var percent: Int = 0
)