package com.nunez.libellis.main.reading

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.LibellisApp
import com.nunez.libellis.R
import com.nunez.libellis.di.AppModule
import com.nunez.libellis.di.DaggerReadingComponent
import com.nunez.libellis.di.ReadingModule
import com.nunez.libellis.entities.Review
import com.nunez.libellis.main.reading.updateProgress.UpdateProgressSheet
import com.nunez.libellis.showSnackbar
import kotlinx.android.synthetic.main.fragment_currently_reading.*
import javax.inject.Inject


class ReadingFragment : Fragment(), ReadingContract.View {

    @Inject lateinit var interactor: ReadingInteractor
    lateinit var presenter: ReadingPrensenter

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

        DaggerReadingComponent.builder()
                .appModule(AppModule(this.activity.application as LibellisApp))
                .readingModule(ReadingModule(this))
                .build()
                .inject(this)

        presenter = ReadingPrensenter(this, interactor)
        presenter.getBooks()

        return view
    }

    override fun showBooks(books: List<Review>) {
        readingRecycler.layoutManager = LinearLayoutManager(activity)
        readingRecycler.setHasFixedSize(true)
        readingRecycler.adapter = ReadingAdapter(books, {
            id ->
            UpdateProgressSheet(id.toString()).show(fragmentManager, "progress_sheet")
        })
    }

    override fun showLoading() {
    }

    override fun showNoBooks() {
    }

    override fun showMessage(message: String, error: Boolean) {
        readingRecycler.showSnackbar(message)
    }

}
