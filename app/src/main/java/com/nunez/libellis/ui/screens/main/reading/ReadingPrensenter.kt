package com.nunez.libellis.ui.screens.main.reading

import com.nunez.libellis.UserPreferenceManager
import com.nunez.libellis.domain.entities.CurrentlyReadingBook

class ReadingPrensenter(
        private val view: ReadingContract.View,
        private val interactor: ReadingContract.Interactor,
        private val userPreferenceManager: UserPreferenceManager
) : ReadingContract.Presenter {

    companion object {
        val TAG = "ReadingPresenter"
    }

    override fun getBooks() {
        showLoadingOrRefreshing()
        fetchBooksFromNetwork()
        getCachedBooks()
    }

    override fun sendReadingBooks(readingBooks: List<CurrentlyReadingBook>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getCachedBooks(){
        interactor.requestBooksObservable().subscribe({
            hideLoadingAndRefreshingIndicator()
            showBooks(it)
        }, {
            it.printStackTrace()
        })
    }

    override fun onBookClicked(id: String, title: String, author: String) {
        view.showUpdateProgress(id, title, author)
    }

    private fun fetchBooksFromNetwork() {
        interactor.fetchBooks().subscribe({
        }, {
            it.printStackTrace()
        })
    }

    private fun hideLoadingAndRefreshingIndicator() {
        view.hideLoading()
        view.hideRefreshing()
    }

    private fun showLoadingOrRefreshing() {
        if (userPreferenceManager.isCurrentlyReadingBooksSavedInCache()) {
            view.showRefreshing()
        } else {
            view.showLoading()
        }
    }

    private fun showBooks(list: List<CurrentlyReadingBook>) {
        if (list.isEmpty()) {
            view.showNoBooksMessage()
        }else{
            view.hideNoBooksMessage()
            view.showBooks(list)
        }
    }

}