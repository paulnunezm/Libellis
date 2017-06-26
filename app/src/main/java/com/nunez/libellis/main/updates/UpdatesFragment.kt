package com.nunez.libellis.main.updates

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.LibellisApp
import com.nunez.libellis.R
import com.nunez.libellis.di.AppModule
import com.nunez.libellis.di.DaggerUpdatesComponent
import com.nunez.libellis.di.UpdatesModule
import com.nunez.libellis.entities.Shelve
import com.nunez.libellis.entities.Update
import com.nunez.libellis.shelves.ModalShelvesBottomSheet
import com.nunez.libellis.showSnackbar
import kotlinx.android.synthetic.main.updates_fragment.*
import javax.inject.Inject

/**
 * Use the [UpdatesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdatesFragment : Fragment(), UpdatesContract.View, UpdatesAdapter.onItemClickListener {


    lateinit var adapter: UpdatesAdapter

    val shelvesBottomSheet: ModalShelvesBottomSheet by lazy { ModalShelvesBottomSheet() }

    @Inject lateinit var interactor: UpdatesInteractor
    @Inject lateinit var presenter: UpdatesPresenter

    companion object {
        /** Use this factory method to create a new instance of this fragment */
        fun newInstance(): UpdatesFragment {
            val fragment = UpdatesFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        DaggerUpdatesComponent.builder()
                .appModule(AppModule(activity.application as LibellisApp))
                .updatesModule(UpdatesModule(view = this))
                .build()
                .inject(this)

        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.updates_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        presenter.requestUpdates()

        adapter = UpdatesAdapter(this.activity, this)

        updatesRecycler.adapter = adapter
        updatesRecycler.layoutManager = GridLayoutManager(this.activity, 1) as RecyclerView.LayoutManager? // TODO: change to auto fit
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //        if (context instanceof OnFragmentInteractionListener) {
        //            mListener = (OnFragmentInteractionListener) context;
        //        } else {
        //            throw new RuntimeException(context.toString()
        //                    + " must implement OnFragmentInteractionListener");
        //        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun showUpdates(updates: List<Update>) {
        adapter.setUpdates(updates)
    }

    override fun showFakeUpdates() {
    }

    override fun showNoBooks() {
    }

    override fun showError(message: String) {
    }

    override fun showShelves(shelves: List<Shelve>) {
        shelvesBottomSheet.addShelvesToShow(shelves,
                { shelveName, bookId ->
                    updatesRecycler.showSnackbar("$shelveName, $bookId")
                    // TODO("implement add to shelve call")
                })
    }

    // Updates adapter listeners
    override fun onUpdateClick(view: UpdatesAdapter.ListenerType, id: String) {
        when (view) {
            UpdatesAdapter.ListenerType.USERNAME_OR_IMAGE -> {
            }
            UpdatesAdapter.ListenerType.BOOK_TITLE_OR_IMAGE -> {
            }
            UpdatesAdapter.ListenerType.ADD_TO_SHELVES -> {
                // Request shelves and present a bottom sheet with a circular progress
                presenter.getShelves()
                shelvesBottomSheet.bookId = id
                shelvesBottomSheet.show(activity.supportFragmentManager, "bottom_sheet")
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}// Required empty public constructor
