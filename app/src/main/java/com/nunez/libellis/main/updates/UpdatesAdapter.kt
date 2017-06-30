package com.nunez.libellis.main.updates

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.Update
import com.nunez.libellis.inflate
import com.nunez.libellis.main.updates.viewHolders.*
import java.util.*

/**
 * An adapter for the updates response.
 */
class UpdatesAdapter(val context: Context,
                     val listener: onItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_COMMENT = 0
        const val TYPE_FRIEND = 1
        const val TYPE_READ_STATUS = 2
        const val TYPE_REVIEW = 3
        const val TYPE_USER_STATUS = 4
    }

    sealed class Listener {
        class AddToShelve(val bookId: String): Listener()
    }

    var updatesList = Collections.emptyList<Update>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            TYPE_FRIEND -> FriendUpdateViewHolder(parent.inflate(R.layout.update_friend), listener)
            TYPE_READ_STATUS -> ReadStatusUpdateViewHolder(parent.inflate(R.layout.update_readstatus), listener)
            TYPE_REVIEW -> ReviewViewHolder(parent.inflate(R.layout.udpate_reviews), listener)
            TYPE_COMMENT -> CommentViewHolder(parent.inflate(R.layout.update_comment), listener)
            else -> UserStatusViewHolder(parent.inflate(R.layout.update_userstatus), listener)
        }

        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as UpdateViewHolder).bindViews(updatesList[position])
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (updatesList[position].updateType) {
            Update.TYPE.READ_STATUS -> TYPE_READ_STATUS
            Update.TYPE.REVIEW -> TYPE_REVIEW
            Update.TYPE.COMMENT -> TYPE_COMMENT
            Update.TYPE.FRIEND -> TYPE_FRIEND
            Update.TYPE.USER_STATUS -> TYPE_USER_STATUS
            else -> 0
        }

        return type
    }

    override fun getItemCount() = updatesList.size

    fun setUpdates(updates: List<Update>) {
        updatesList = updates
        notifyDataSetChanged()
    }

    interface onItemClickListener {

        /** Manages the click listeners for all the views
         * of each UpdateViewHolder.
         *
         * Sends the type of listener, this says which view
         * was click so the function that it listening knows
         * how to react.
         * */
        fun clicked(listenerType: Listener)
    }

}