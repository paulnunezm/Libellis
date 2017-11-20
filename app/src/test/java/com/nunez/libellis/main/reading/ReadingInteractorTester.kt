package com.nunez.libellis.main.reading

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.nunez.libellis.ConnectivityChecker
import com.nunez.libellis.EntityMapperBehavior
import com.nunez.libellis.entities.CurrentlyReadingBook
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.ReactiveStore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class ReadingInteractorTester {

    lateinit var interactor: ReadingInteractor
    val repository = mock<ReactiveStore<String, CurrentlyReadingBook>>()
    val connectivityChecker = mock<ConnectivityChecker>()
    val goodreadService = mock<GoodreadsService>()
    val mapper = mock<EntityMapperBehavior>()

    @Before
    fun setup() {
        interactor = ReadingInteractor("userId", repository, mapper, connectivityChecker, goodreadService)
    }

    @Test
    fun onRequestBooksShouldReturnCurrentlyBooksFlowable() {
        // given
        val booksFlowable = Flowable.create<List<CurrentlyReadingBook>>({
            it.onNext(emptyList())
            it.onComplete()
        }, BackpressureStrategy.MISSING)

        whenever(repository.all).thenReturn(booksFlowable)

        // when
        val returnedFlowable = interactor.requestBooks()

        // then
        val assertMessage = "Expected to return a flowable from the repository"
        assertEquals(assertMessage, booksFlowable, returnedFlowable)
    }
}