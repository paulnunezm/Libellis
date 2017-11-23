package com.nunez.libellis

import com.nunez.libellis.entities.raw.Review
import com.nunez.libellis.entities.raw.ReviewAuthor
import com.nunez.libellis.entities.raw.ReviewBook
import org.junit.Assert
import org.junit.Test
import java.util.*

class EntityMapperTest {

    val mapper = EntityMapper()

    @Test
    fun mapReviewListToCurrentlyReadingShouldReturnEmptyListWhenSomethingNull() {
        // given
        val reviewsList = listOf(Review(), Review(), Review())

        // when
        val mappedList = mapper.mapReviewListToCurrentlyReading(reviewsList)

        // then
        Assert.assertTrue(mappedList.isEmpty())
    }

    @Test
    fun shouldReturnSameLengthListAfterMapped(){
        // given
        val reviews = listOf(generateReview(), generateReview(), generateReview())

        // when
        val mappedList = mapper.mapReviewListToCurrentlyReading(reviews)

        // then
        Assert.assertEquals("Same lenght list", reviews.size, mappedList.size)

    }


    private fun generateReview(): Review{
        val author = ReviewAuthor("2", "BramStoker")
        val authors = ArrayList(listOf(author))
        val book = ReviewBook("103", "Dracula", authors, 350)
        return Review("1344", book)
    }

}