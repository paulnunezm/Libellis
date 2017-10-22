package com.nunez.libellis.main.reading

import com.nunez.libellis.entities.Review

class ReadingPrensenter
 constructor(
        val view: ReadingContract.View,
        val interactor: ReadingContract.Interactor
) : ReadingContract.Presenter {

    override fun getBooks() {
        interactor.requestBooks().subscribe({
            view.hideLoading()
            sendReadingBooks(it)
        },{
            view.showMessage("Ups! Something seems wrong", true)
        })
    }

    override fun sendReadingBooks(readingBooks: List<Review>) {
        if(readingBooks.isNotEmpty()){
            view.showBooks(readingBooks)
        }else{
            view.showNoBooks()
        }
    }
}