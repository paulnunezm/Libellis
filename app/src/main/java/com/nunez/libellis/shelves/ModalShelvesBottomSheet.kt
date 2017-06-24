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

    companion object {
        fun newInstance(): ModalShelvesBottomSheet {
            return ModalShelvesBottomSheet()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.shelve_bottom_sheet, container, false)
    }

    fun addShelvesToShow(shelves: List<Shelve>) { // TODO: addCallback
        val adapter = ShelvesAdapter(shelves)
        shelvesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        shelvesRecycler.adapter = adapter
        shelvesRecycler.visibility = View.VISIBLE
        shelvesProgress.visibility = View.GONE
    }
}