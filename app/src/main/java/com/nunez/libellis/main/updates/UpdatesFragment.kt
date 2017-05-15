package com.nunez.libellis.main.updates

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.Update
import kotlinx.android.synthetic.main.updates_fragment.*

/**
 * Use the [UpdatesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdatesFragment : Fragment(), UpdatesContract.View, UpdatesAdapter.onItemClickListener {

    lateinit var adapter: UpdatesAdapter

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        fun newInstance(): UpdatesFragment {
            val fragment = UpdatesFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.updates_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val presenter = UpdatesPresenter(this)
        val interactor = UpdatesInteractor(this.activity)

        interactor.updatesPresenter = presenter
        presenter.setUpdatesInteractor(interactor)

        adapter = UpdatesAdapter(this.activity, this)

        updatesRecycler.adapter = adapter
        updatesRecycler.layoutManager = GridLayoutManager(this.activity, 1) // TODO: change to auto fit
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

    // Updates adapter listeners
    override fun onUserNameOrImageClicked() {
    }

    override fun onBookTitleOrImageClicked() {
    }

    override fun onAuthorNameCliked() {
    }

    override fun onLikeBtnClicked() {
    }

    override fun onCommentBtnClicked() {
    }

    override fun onWantToReadClicked() {
    }

    override fun onAddToShelvesClicked() {
    }

    override fun onCommentClicked() {
    }

    override fun onFriendNameOrImageClicked() {
    }
    // end of updates aadapter listeners

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
