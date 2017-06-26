package com.nunez.libellis.shelves

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.Shelve
import kotlinx.android.synthetic.main.shelve_bottom_sheet.*


class ModalShelvesBottomSheet : BottomSheetDialogFragment() {

    var bookId: String = ""

    companion object {
        fun newInstance(): ModalShelvesBottomSheet {
            return ModalShelvesBottomSheet()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(bookId.isEmpty()) throw IllegalStateException("book id must be set before shown been called")
        return inflater?.inflate(R.layout.shelve_bottom_sheet, container, false)
    }

    fun addShelvesToShow(shelves: List<Shelve>, listener: (String, String) -> (Unit)) {
        val adapter = ShelvesAdapter(shelves, true, {
            shelveName ->
            listener(bookId, shelveName)
            this.dismiss()
        })
        shelvesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        shelvesRecycler.adapter = adapter
        shelvesRecycler.visibility = View.VISIBLE
        shelvesProgress.visibility = View.GONE
    }
}