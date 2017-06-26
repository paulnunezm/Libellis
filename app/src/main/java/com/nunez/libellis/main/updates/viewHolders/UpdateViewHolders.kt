package com.nunez.libellis.main.updates.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.nunez.libellis.R
import com.nunez.libellis.entities.*
import com.nunez.libellis.loadImage
import com.nunez.libellis.main.updates.UpdatesAdapter
import kotlinx.android.synthetic.main.udpate_reviews.view.*
import kotlinx.android.synthetic.main.update_book.view.*
import kotlinx.android.synthetic.main.update_comment.view.*
import kotlinx.android.synthetic.main.update_friend.view.*
import kotlinx.android.synthetic.main.update_user.view.*
import kotlinx.android.synthetic.main.update_userstatus.view.*


abstract class UpdateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /** NOTE: This variables can be null depending of the update type */

    // book layout vars
    val bookTitle = itemView.bookTitle
    val bookImage = itemView.bookImage
    val authorName = itemView.authorName
    val addToShelve = itemView.addToShelveBtn

    // user layout vars
    val userName = itemView.userName
    val status = itemView.userStatus
    val updateTime = itemView.updatedTime

    abstract fun bindViews(update: Update)
}

private fun bindBook(
        viewHolder: UpdateViewHolder,
        book: Book,
        posterUrl: String,
        listener: UpdatesAdapter.onItemClickListener) {

    with(viewHolder){
        bookTitle.text = book.title
        authorName.text = book.authors[0].name // TODO: add the others author names
        bookImage.loadImage(posterUrl)

        addToShelve.setOnClickListener {
            listener.onUpdateClick(UpdatesAdapter.ListenerType.ADD_TO_SHELVES, book.id)
        }

        // TODO: set other listeners

    }
}

private fun bindUser(itemView: View, user: User, updatedAt: String, status: String, listener: UpdatesAdapter.onItemClickListener) {

    itemView.userName.text = user.name
    if (user.imageUrl.isNotEmpty()) itemView.userImage.loadImage(user.imageUrl)
    itemView.updatedTime.text = updatedAt

    if (status.isEmpty())
        itemView.userStatus.visibility = View.GONE
    else
        itemView.userStatus.text = status

//    itemView.userName.setOnClickListener { listener.onUserNameOrImageClicked() }
//    itemView.userImage.setOnClickListener { listener.onUserNameOrImageClicked() }
}

class ReviewViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : UpdateViewHolder(itemView) {

    override fun bindViews(update: Update) {
        val mUpdate = update as ReviewUpdate

        with(mUpdate) {
            itemView.ratingBar.rating = rating.toFloat()
            bindUser(itemView, user, updatedAt, "", listener)
            bindBook(viewHolder = this@ReviewViewHolder,
                    book = book,
                    posterUrl = bookImageUrl,
                    listener = listener)
        }
    }
}

class CommentViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : UpdateViewHolder(itemView) {

    override fun bindViews(update: Update) {
        val commentUpdate = update as CommentUpdate

        with(commentUpdate) {
            bindUser(itemView, user, updatedAt, status, listener)
            itemView.comment.text = commentUpdate.comment
        }
    }
}

class FriendUpdateViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : UpdateViewHolder(itemView) {

    override fun bindViews(update: Update) {
        val friendUpdate = update as FriendUpdate

        val status = itemView.context.resources.getString(R.string.update_user_is_now_friend_with)

        with(friendUpdate) {
            itemView.friendImage.loadImage(friendImageUrl)
            itemView.friendName.text = friendName
//            itemView.friendImage.setOnClickListener { listener.onFriendNameOrImageClicked() }
//            itemView.friendName.setOnClickListener { listener.onFriendNameOrImageClicked() }
            bindUser(itemView, user, updatedAt, status, listener)
        }
    }
}

class ReadStatusUpdateViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : UpdateViewHolder(itemView) {

    override fun bindViews(update: Update) {
        val readStatus = update as ReadStatusUpdate
        bindUser(itemView, update.user, update.updatedAt, update.status, listener)
        bindBook(viewHolder = this,
                book = readStatus.review.book,
                posterUrl = "",
                listener = listener)

        // TODO: implement book cover Image here, right now is not parsed
    }
}

class UserStatusViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : UpdateViewHolder(itemView) {

    override fun bindViews(update: Update) {
        val userStatus = update as UserStatusUpdate

        itemView.progressBar.progress = update.status.percent.toInt()
        bindUser(itemView, update.user, update.updateAt, userStatus.status.page, listener)
        bindBook(viewHolder = this,
                book = userStatus.status.book,
                posterUrl = "",
                listener = listener)
        // TODO: implement book cover image here, right now is not parsed
    }
}

