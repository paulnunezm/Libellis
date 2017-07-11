package com.nunez.libellis.main.reading.updateProgress

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.nunez.libellis.R
import kotlinx.android.synthetic.main.reading_update_bottom_sheet.*


class UpdateProgressSheet: BottomSheetDialogFragment() {

    val bookTotalPages = 600

    companion object {
        fun newInstance(): UpdateProgressSheet{
            return UpdateProgressSheet()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.reading_update_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViews()
    }

    fun prepareViews(){

        seekBar.max = bookTotalPages

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val currentPage = if (progress > 0) progress else 0
                val currentPercent = progress*100/bookTotalPages
                percent.text = currentPercent.toString() + "%"
                pages.text = currentPage.toString()
            }
        })
    }
}