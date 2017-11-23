package com.nunez.libellis.main.reading

import com.nhaarman.mockito_kotlin.*
import com.nunez.libellis.UserPreferenceManager
import com.nunez.libellis.entities.CurrentlyReadingBook
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class ReadingPrensenterTest {

    lateinit var interactor: ReadingContract.Interactor
    lateinit var view: ReadingContract.View
    lateinit var presenter: ReadingPrensenter
    private var userPreferenceManager: UserPreferenceManager = mock()
    private val list = listOf(CurrentlyReadingBook(), CurrentlyReadingBook())


//    [UnitOfWork__StateUnderTest__ExpectedBehavior]

    @Before
    fun setup() {
        interactor = mock {
            on { requestBooksObservable() } doReturn Flowable.just(emptyList())
        }
        view = mock()
        presenter = ReadingPrensenter(view, interactor, userPreferenceManager)
    }

    @Test
    fun getBooks_shouldCallRequestBooks() {
        setMockInteractor_requestBooksObservable_filledList_fetchBooks_Complete()

        // when
        presenter.getBooks()

        // then
        verify(interactor).requestBooksObservable()
    }

    @Test
    fun getBooks_emptyList_showEmptyScreenAndHidesBothLoading() {//onRequestBooksReturnedEmptyShouldShowEmptyScreen() {
        // when
        setMockInteractor_requestBooksObservable_emptyList_fetchBooks_Complete()

        // when
        presenter.getBooks()

        // then
        verify(view).hideLoading()
        verify(view).showNoBooksMessage()
        verify(view).hideRefreshing()
    }

    @Test
    fun getBooks_filledList_neverShowEmptyScreen() {
        //given
        setMockInteractor_requestBooksObservable_filledList_fetchBooks_Complete()

        // when
        presenter.getBooks()

        // then
        verify(view, never()).showNoBooksMessage()
    }

    @Test
    fun getBooks_filledList_showBooksAndHideLoadingAndRefresh() {
        //given
        setMockInteractor_requestBooksObservable_filledList_fetchBooks_Complete()

        // when
        presenter.getBooks()

        // then
        verify(view).hideLoading()
        verify(view).hideRefreshing()
        verify(view).showBooks(list)
    }

    @Test
    fun getBooks_currentlyReadingNotCached_requestBooksToApi_getBookObservable_showOnlyLoadingNotRefreshing() {
        // given
        whenever(userPreferenceManager.isCurrentlyReadingBooksSavedInCache()).thenReturn(false)
        setMockInteractor_requestBooksObservable_emptyList_fetchBooks_Complete()

        // when
        presenter.getBooks()

        // then
        verify(interactor).fetchBooks()
        verify(interactor).requestBooksObservable()
        verify(view).showLoading()
        verify(view, never()).showRefreshing()
    }

    @Test
    fun getBooks_currentlyReadingCached_showOnlyRefreshLoading() {
        // given
        whenever(userPreferenceManager.isCurrentlyReadingBooksSavedInCache()).thenReturn(true)
        setMockInteractor_requestBooksObservable_filledList_fetchBooks_Complete()

        // when
        presenter.getBooks()

        // then
        verify(interactor).fetchBooks()
        verify(interactor).requestBooksObservable()
        verify(view, never()).showLoading()
        verify(view).showRefreshing()
    }


    /*
    **********
    * HElPERS
    **********
     */

    private fun setMockInteractor_requestBooksObservable_filledList_fetchBooks_Complete() {
        //given
        interactor = mock {
            on { requestBooksObservable() } doReturn Flowable.just(list)
            on { fetchBooks() } doReturn Completable.complete()
        }
        instantiatePresenter()
    }

    private fun setMockInteractor_requestBooksObservable_emptyList_fetchBooks_Complete() {
        //given
        interactor = mock {
            on { requestBooksObservable() } doReturn Flowable.just(emptyList())
            on { fetchBooks() } doReturn Completable.complete()
        }
        instantiatePresenter()
    }

    private fun instantiatePresenter(){
        presenter = ReadingPrensenter(view, interactor, userPreferenceManager)
    }
}