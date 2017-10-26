package com.nunez.libellis.main.reading

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.Review
import com.nunez.libellis.gone
import com.nunez.libellis.main.reading.updateProgress.UpdateProgressSheet
import com.nunez.libellis.repository.SignedRetrofit
import com.nunez.libellis.showSnackbar
import com.nunez.libellis.visible
import kotlinx.android.synthetic.main.fragment_currently_reading.*


class ReadingFragment : Fragment(), ReadingContract.View {

    lateinit var interactor: ReadingInteractor
    lateinit var presenter: ReadingPrensenter

    lateinit var parentView: View

    companion object {
        val TAG = "CurrentlyReading"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_currently_reading, container, false)
        parentView = view.findViewById(R.id.readingParent)

        val context = this.context
        interactor = ReadingInteractor(context, SignedRetrofit(context))
        presenter = ReadingPrensenter(this, interactor)
        presenter.getBooks()

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView.startShimmerAnimation()
    }

    override fun showBooks(books: List<Review>) {
        readingRecycler.layoutManager = LinearLayoutManager(activity)
        readingRecycler.setHasFixedSize(true)
        readingRecycler.adapter = ReadingAdapter(books, { id, title, authorName ->
            val updateProgressSheet = UpdateProgressSheet()
            val bundle = Bundle()
            bundle.putString(UpdateProgressSheet.EXTRA_ID, id)
            bundle.putString(UpdateProgressSheet.EXTRA_TITLE, title)
            bundle.putString(UpdateProgressSheet.EXTRA_AUTHOR, authorName)
            updateProgressSheet.arguments = bundle
            updateProgressSheet.show(fragmentManager, "progress_sheet")
        })
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        loadingView.gone()
    }

    override fun showErrorMessage() {
        errorMessage.visible()
    }

    override fun showNoBooksMessage() {
        noBooksMessage.visible()
    }

    override fun showMessage(message: String, error: Boolean) {
        loadingView.stopShimmerAnimation()
        parentView.showSnackbar(message)
    }

}
