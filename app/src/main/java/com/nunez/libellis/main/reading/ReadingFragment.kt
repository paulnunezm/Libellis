package com.nunez.libellis.main.reading

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.*
import com.nunez.libellis.entities.CurrentlyReadingBook
import com.nunez.libellis.main.reading.updateProgress.UpdateProgressSheet
import com.nunez.libellis.repository.CurrentlyReadingBookRespository
import com.nunez.libellis.repository.GoodreadsService
import com.nunez.libellis.repository.SignedRetrofit
import io.realm.Realm
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_currently_reading, container, false)
        parentView = view.findViewById(R.id.readingParent)

        initializeDependencies()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readingRecycler.layoutManager = LinearLayoutManager(activity)
        readingRecycler.setHasFixedSize(true)

        presenter.getBooks()
    }

    override fun showBooks(books: List<CurrentlyReadingBook>) {
        readingRecycler.scheduleLayoutAnimation()
        readingRecycler.adapter = ReadingAdapter(books) { id, title, authorName ->
            presenter.onBookClicked(id, title, authorName)
        }
    }

    override fun animateBooks() {
        readingRecycler.scheduleLayoutAnimation()
    }

    override fun showUpdateProgress(id: String, title: String, author: String) {
        val updateProgressSheet = UpdateProgressSheet()
        val bundle = Bundle().apply {
            putString(UpdateProgressSheet.EXTRA_ID, id)
            putString(UpdateProgressSheet.EXTRA_TITLE, title)
            putString(UpdateProgressSheet.EXTRA_AUTHOR, author)
        }

        fragmentManager?.let {
            updateProgressSheet.arguments = bundle
            updateProgressSheet.show(it, "progress_sheet")
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showErrorMessage() {
        errorMessage.visible()
    }

    override fun showNoBooksMessage() {
        noBooksMessage.visible()
    }

    override fun hideNoBooksMessage() {
        noBooksMessage.gone()
    }

    override fun showMessage(message: String, error: Boolean) {
        parentView.showSnackbar(message)
    }

    override fun showRefreshing() {
    }

    override fun hideRefreshing() {
    }

    private fun initializeDependencies() {

        val context = this.context ?: return
        val userPrefManager = UserPrefsManager(context)
        val realm = Realm.getDefaultInstance()
        val repository = CurrentlyReadingBookRespository(realm, userPrefManager)
        val mapper = EntityMapper()
        val connectivityChecker = ConnectivityCheckerImpl(context)
        val retrofit = SignedRetrofit(context).instance
        val GoodreadsService = retrofit.create(GoodreadsService::class.java)

        interactor = ReadingInteractor(userPrefManager.userId, repository, mapper, connectivityChecker, GoodreadsService)
        presenter = ReadingPrensenter(this, interactor, userPrefManager)

    }
}
