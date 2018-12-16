package com.nunez.libellis

import com.nunez.libellis.domain.entities.Author
import com.nunez.libellis.domain.entities.CurrentlyReadingBook
import com.nunez.libellis.domain.entities.raw.Review
import com.nunez.libellis.domain.entities.raw.ReviewAuthor
import com.nunez.libellis.domain.entities.raw.ReviewBook
import io.realm.RealmList

interface EntityMapperBehavior {
    fun mapReviewListToCurrentlyReading(reviews: List<Review>?): List<CurrentlyReadingBook>
}

class EntityMapper : EntityMapperBehavior {

    override fun mapReviewListToCurrentlyReading(reviews: List<Review>?): RealmList<CurrentlyReadingBook> {
        if (reviews != null) {
            val list = RealmList<CurrentlyReadingBook>()
            for (review: Review? in reviews) {
                review.let {
                    val book = it?.book
                    book?.let {
                        list.add(parseReviewBookToCurrentlyReading(book))
                    }
                }
            }
           return list
        } else {
            return RealmList()
        }
    }

    private fun parseReviewBookToCurrentlyReading(book: ReviewBook): CurrentlyReadingBook {
        val authors = parseReviewAuthorToAuthor(book.authors)
        return CurrentlyReadingBook(book.id, book.title, authors)
    }

    private fun parseReviewAuthorToAuthor(reviewAuthors: ArrayList<ReviewAuthor>?): RealmList<Author> {
        val authors = RealmList<Author>()
        reviewAuthors?.map { author: ReviewAuthor? ->
            author?.let {
                authors.add(Author(it.id, it.name))
            }
        }
        return authors
    }
}

