package com.nunez.libellis.ui.screens.main.reading.updateProgress

//import com.nunez.libellis.R.id.seekBar
import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.data.network.GoodreadsService
import com.nunez.libellis.data.network.SignedRetrofit
import kotlinx.android.synthetic.main.reading_update_bottom_sheet.*


class UpdateProgressSheet : BottomSheetDialogFragment(), UpdateProgressSheetContract.View {

    var reviewId: String = ""

    lateinit var presenter: UpdateProgressPresenter
    lateinit var interactor: UpdateProgressSheetInteractor

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_TITLE = "title"
        const val EXTRA_AUTHOR = "author"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.reading_update_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString(EXTRA_ID) ?: ""
        val title = arguments?.getString(EXTRA_TITLE) ?: ""
        val author = arguments?.getString(EXTRA_AUTHOR) ?: ""
        updateProgressLayout.bookId = id

        instantiateDependencies(id, title, author)
        setListeners()
    }

    override fun setAuthorName(name: String) {
        updateProgressLayout.authorName = name
    }

    override fun setBookTitle(title: String) {
        updateProgressLayout.title = title
    }

    override fun showLoading() {}

    override fun dismissSheet() {
        dismiss()
    }

    override fun showError() {
    }

    private fun instantiateDependencies(id: String, title: String, author: String) {
        context?.let {
            interactor = UpdateProgressSheetInteractor(it, getGoodreadService(it))
            presenter = UpdateProgressPresenter(id, title, author, this, interactor)
        }
    }

    private fun getGoodreadService(context: Context): GoodreadsService {
           return SignedRetrofit(context).instance.create(GoodreadsService::class.java)
    }

    private fun setListeners() {
        updateButton.setOnClickListener {
            presenter.onUpdateClicked(updateProgressLayout.values)
        }
        cancelButton.setOnClickListener { presenter.onCancelClicked() }
    }
}