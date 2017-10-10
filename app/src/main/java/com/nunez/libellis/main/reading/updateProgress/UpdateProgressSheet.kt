package com.nunez.libellis.main.reading.updateProgress

//import com.nunez.libellis.R.id.seekBar
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import kotlinx.android.synthetic.main.reading_update_bottom_sheet.*


class UpdateProgressSheet() : BottomSheetDialogFragment(), UpdateProgressSheetContract.View {

    var reviewId: String = ""

    lateinit var presenter: UpdateProgressPresenter
    val interactor: UpdateProgressSheetInteractor by lazy { UpdateProgressSheetInteractor() }

    companion object {
        const val EXTRA_ID = "id"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.reading_update_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = UpdateProgressPresenter(reviewId, this, interactor)

        updateButton.setOnClickListener {
            //presenter.onUpdateClicked()
        }
        cancelButton.setOnClickListener { presenter.onCancelClicked() }
    }


    override fun setAuthorName(name: String) {
    }

    override fun setBookTitle(title: String) {
    }

    override fun showCommentInput() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRatingStars() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {}

    override fun dismissSheet() {
        dismiss()
    }

    override fun showError() {
        // TODO: show error
    }
}