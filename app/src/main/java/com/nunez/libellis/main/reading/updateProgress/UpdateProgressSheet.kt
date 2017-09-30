package com.nunez.libellis.main.reading.updateProgress

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.nunez.libellis.R
import kotlinx.android.synthetic.main.reading_update_bottom_sheet.*


class UpdateProgressSheet(
        var reviewId: String = "") : BottomSheetDialogFragment(), UpdateProgressSheetContract.View {

    lateinit var presenter : UpdateProgressPresenter
    val interactor : UpdateProgressSheetInteractor by lazy { UpdateProgressSheetInteractor() }

    companion object {
        fun newInstance(reviewId: String): UpdateProgressSheet{
            return UpdateProgressSheet(reviewId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.reading_update_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = UpdateProgressPresenter(reviewId, this, interactor)

        updateButton.setOnClickListener { presenter.onUpdateClicked() }
        cancelButton.setOnClickListener { presenter.onCancelClicked() }
    }


    override fun setAuthorName(name: String) {
        bookAuthor.text = name
    }

    override fun setBookTitle(title: String) {
        bookTitle.text = title
    }

    override fun setMaxValue(value: Int) {
        seekBar.max = value
    }

    override fun setPercentage(percentage: Int) {
        percent.text = percentage.toString()
    }

    override fun setPage(page: Int) {
        pages.text = page.toString()
    }
    override fun enableSeekBarListener() {
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                presenter.calculateValuesFromPages(progress)
            }
        })
    }
    override fun showLoading() {}

    override fun dismissSheet() {
        dismiss()
    }

    override fun showError() {
        // TODO: show error
    }
}