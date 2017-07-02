package com.nunez.libellis.entities

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
        @field:ElementList var review:Review? = null)


@Root(name = "review", strict = false)
class Review(
        @field:Element var id:Int = 0,
        @field:Element var book: ReviewBook? = null
)

@Root(name = "book", strict = false)
data class ReviewBook(
         @field:Element var id: String = "",
         @field:Element var title: String = "",
         @field:Element var averageRating: String = "",
         @field:Element var ratingsCount: String = "",
         @field:Element var reviewsCount: String = "",
         @field:ElementList var authors: ArrayList<ReviewAuthor> = ArrayList()
)

@Root(name = "author", strict = false)
data class ReviewAuthor(
         @field:Element var id: String = "",
         @field:Element var name: String = "",
         @field:Element var imageUrl: String = "",
         @field:Element var link: String = ""
)
