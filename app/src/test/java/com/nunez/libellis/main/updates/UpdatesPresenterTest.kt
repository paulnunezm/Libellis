package com.nunez.libellis.main.updates

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nunez.libellis.entities.raw.*
import org.junit.Test
/**
 * Created by paulnunez on 5/9/17.
 */
class UpdatesPresenterTest {

    val view = mock<UpdatesContract.View>()
    val interactor = mock<UpdatesContract.Interactor>()
    val presenter = UpdatesPresenter(view, interactor)

    @Test
    fun requestUpdates() {
        /**
         * need to test rxJava
         */
        // when
//        val subs = TestObserver<List<Update>>()
//        interactor.requestUpdates().subscribe(subs)
    }


    @Test
    fun sendUpdatesToViewShouldPass() {
        // given
        val list = ArrayList<Update>()
        list.add(FriendUpdate("", User(), "", "", "", ""))
        list.add(ReviewUpdate(User(), "", "", "", "", BookRaw()))

        //when
        presenter.sendUpdates(list)

        // then
        verify(view).showUpdates(list)
    }

    @Test
    fun shouldShowNoBookScreenWhenEmptyListIsReturned() {
        // given
        val list = ArrayList<Update>()

        // when
        presenter.sendUpdates(list)

        // then
        verify(view).showNoBooks()
    }

    @Test
    fun shouldShowErrorMessage() {
        // given
        val message = "Message"

        //when
        presenter.showError(message)

        // then
//        verify(view).showError(message)
    }

    @Test
    fun shouldNotShowErrorMessageIfMessageIsEmpty(){
        // given
        val message = ""

        // when
        presenter.showError(message)

        //then
//        verify(view, never()).showError(message)
    }

}