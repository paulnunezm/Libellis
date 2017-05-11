package com.nunez.libellis.main.updates

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.Update

/**
 * Use the [UpdatesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdatesFragment : Fragment(), UpdatesContract.View {

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

        val presenter = UpdatesPresenter(this)
        val interactor = UpdatesInteractor(this.activity)

        interactor.updatesPresenter = presenter
        presenter.setUpdatesInteractor(interactor)

        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_updates, container, false)
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
    }

    override fun showFakeUpdates() {
    }

    override fun showNoBooks() {
    }

    override fun showError(message: String) {
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
