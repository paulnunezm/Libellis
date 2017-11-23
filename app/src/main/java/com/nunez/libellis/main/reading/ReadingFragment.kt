package com.nunez.libellis.main.reading

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.*
import com.nunez.libellis.entities.CurrentlyReadingBook
import com.nunez.libellis.entities.raw.Review
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

    var isResuming = false

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
        val userPrefManager = UserPrefsManager(context)
        val realm = Realm.getDefaultInstance()
        val repository = CurrentlyReadingBookRespository(realm, userPrefManager)
        val mapper = EntityMapper()
        val connectivityChecker = ConnectivityCheckerImpl(context)
        val retrofit = SignedRetrofit(context).instance
        val GoodreadsService = retrofit.create(GoodreadsService::class.java)

        interactor = ReadingInteractor(userPrefManager.userId, repository, mapper, connectivityChecker, GoodreadsService)
        presenter = ReadingPrensenter(this, interactor, userPrefManager)
        presenter.getBooks()

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView.intensity = 0.01f
        loadingView.startShimmerAnimation()
    }

    override fun onResume() {
        super.onResume()
        isResuming = true
    }

    override fun showBooks(books: List<CurrentlyReadingBook>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Deprecated("use showBooks")
    fun OLDshowBooks(books: List<Review>) {
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

        readingRecycler.scheduleLayoutAnimation()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        loadingView.stopShimmerAnimation()
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

    override fun showRefreshing() {
    }

    override fun hideRefreshing() {
    }
}
