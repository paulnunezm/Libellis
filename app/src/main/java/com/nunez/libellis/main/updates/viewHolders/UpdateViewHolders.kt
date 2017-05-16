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

/**
 * Contains all the update view holders.
 */
interface UpdateViewHolder {
    fun bindViews(update: Update)
}

private fun bindBook(itemView: View, book: Book, imageUrl: String, listener: UpdatesAdapter.onItemClickListener) {
    with(itemView) {
        bookTitle.text = book.title
        authorName.text = book.authors[0].name

        if(imageUrl.isNotEmpty() )bookImage.loadImage(imageUrl)

        bookTitle.setOnClickListener { listener.onBookTitleOrImageClicked() }
        bookImage.setOnClickListener { listener.onBookTitleOrImageClicked() }
        authorName.setOnClickListener { listener.onAuthorNameClicked() }
        likeBtn.setOnClickListener { listener.onLikeBtnClicked() }
        commentBtn.setOnClickListener { listener.onCommentBtnClicked() }
//        comment.setOnClickListener { listener.onCommentClicked() }
        wanToReadBtn.setOnClickListener { listener.onWantToReadClicked() }
        addToShelveBtn.setOnClickListener { listener.onAddToShelvesClicked() }
    }
}

private fun bindUser(itemView: View, user: User, updatedAt: String, status: String, listener: UpdatesAdapter.onItemClickListener) {

    itemView.userName.text = user.name
    if(user.imageUrl.isNotEmpty()) itemView.userImage.loadImage(user.imageUrl)
    itemView.updatedTime.text = updatedAt

    if (status.isEmpty())
        itemView.userStatus.visibility = View.GONE
    else
        itemView.userStatus.text = status

    itemView.userName.setOnClickListener { listener.onUserNameOrImageClicked() }
    itemView.userImage.setOnClickListener { listener.onUserNameOrImageClicked() }
}

class ReviewViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : RecyclerView.ViewHolder(itemView), UpdateViewHolder {

    override fun bindViews(update: Update) {
        val mUpdate = update as ReviewUpdate

        with(mUpdate) {
            itemView.ratingBar.rating = rating.toFloat()
            bindUser(itemView, user, updatedAt, "", listener)
            bindBook(itemView, book, bookImageUrl, listener)
        }
    }
}

class CommentViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : RecyclerView.ViewHolder(itemView), UpdateViewHolder {

    override fun bindViews(update: Update) {
        val commentUpdate = update as CommentUpdate

        with(commentUpdate){
            bindUser(itemView,user, updatedAt, status, listener)
            itemView.comment.text = commentUpdate.comment
        }
    }
}

class FriendUpdateViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
    override fun bindViews(update: Update) {
        val friendUpdate = update as FriendUpdate

        val status = itemView.context.resources.getString(R.string.update_user_is_now_friend_with)

        with(friendUpdate) {
            itemView.friendImage.loadImage(friendImageUrl)
            // TODO: implement friend name
            itemView.friendImage.setOnClickListener { listener.onFriendNameOrImageClicked() }
            itemView.friendName.setOnClickListener { listener.onFriendNameOrImageClicked() }
            bindUser(itemView, user, updatedAt, status, listener)
        }
    }
}

class ReadStatusUpdateViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : RecyclerView.ViewHolder(itemView), UpdateViewHolder {

    override fun bindViews(update: Update) {
        val readStatus = update as ReadStatusUpdate
        bindUser(itemView, update.user, update.updatedAt, update.status, listener)
        bindBook(itemView, update.review.book, "", listener)

        // TODO: implement book cover Image here
    }
}

class UserStatusViewHolder(itemView: View, val listener: UpdatesAdapter.onItemClickListener)
    : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
    override fun bindViews(update: Update) {
        val userStatus = update as UserStatusUpdate

        itemView.progressBar.progress = update.status.percent.toInt()
        bindUser(itemView, update.user, update.updateAt, userStatus.status.page, listener)
        bindBook(itemView, update.status.book, "", listener)
        // TODO: implement book cover image here
    }
}

