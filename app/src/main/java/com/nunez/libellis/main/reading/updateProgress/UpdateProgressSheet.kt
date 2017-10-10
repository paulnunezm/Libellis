package com.nunez.libellis.main.reading.updateProgress

//import com.nunez.libellis.R.id.seekBar
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nunez.libellis.R
import com.nunez.libellis.repository.GoodreadsService
import kotlinx.android.synthetic.main.reading_update_bottom_sheet.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class UpdateProgressSheet() : BottomSheetDialogFragment(), UpdateProgressSheetContract.View {

    var reviewId: String = ""

    lateinit var presenter: UpdateProgressPresenter
    lateinit var interactor: UpdateProgressSheetInteractor

    companion object {
        const val EXTRA_ID = "id"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.reading_update_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateDependencies()
        setListeners()
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

    private fun instantiateDependencies() {
        interactor = UpdateProgressSheetInteractor(getGoodreadService())
        presenter = UpdateProgressPresenter(reviewId, this, interactor)
    }

    private fun getGoodreadService(): GoodreadsService{
        val client = OkHttpClient().newBuilder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(GoodreadsService.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build()
        return retrofit.create(GoodreadsService::class.java)
    }

    private fun setListeners(){
        updateButton.setOnClickListener {
            //presenter.onUpdateClicked()
        }
        cancelButton.setOnClickListener { presenter.onCancelClicked() }
    }
}