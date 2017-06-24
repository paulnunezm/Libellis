package com.nunez.libellis.entities

/**
 * Created by paulnunez on 4/29/17
 */
data class Book(
        var id: String = "",
        var title: String = "",
        var averageRating: String = "",
        var ratingsCount: String = "",
        var reviewsCount: String = "",
        var authors: ArrayList<Author> = ArrayList()
)

data class Author(
        var id: String = "",
        var name: String = "",
        var imageUrl: String = "",
        var link: String = ""
)
