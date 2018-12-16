package com.nunez.libellis.domain.entities.raw

/**
 * Created by paulnunez on 4/29/17
 */
data class BookRaw(
        var id: String = "",
        var title: String = "",
        var averageRating: String = "",
        var ratingsCount: String = "",
        var reviewsCount: String = "",
        var authors: ArrayList<AuthorRaw> = ArrayList()
)

data class AuthorRaw(
        var id: String = "",
        var name: String = "",
        var imageUrl: String = "",
        var link: String = ""
)
