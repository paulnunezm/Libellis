package com.nunez.libellis.main.reading

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.Review
import com.nunez.libellis.inflate
import kotlinx.android.synthetic.main.reading_item.view.*

class ReadingAdapter(var currentlyReading: List<Review>,
                     val listener: (String, String, String) -> Unit
) : RecyclerView.Adapter<ReadingAdapter.ReadingViewHolder>() {

    override fun onBindViewHolder(holder: ReadingViewHolder?, position: Int) {
        holder?.bindViews(currentlyReading[position])
    }

    override fun getItemCount(): Int = currentlyReading.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReadingViewHolder {
        return ReadingViewHolder(parent?.inflate(R.layout.reading_item) as View, listener)
    }


    class ReadingViewHolder(
            itemView: View,
            val listener:(String, String, String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        val container = itemView.readingContainer
        val bookTitle = itemView.bookTitle
        val authorName = itemView.authorName
        fun bindViews(reading: Review) {
            with(reading) {
                val title = book?.title as String
                val authorNames = book?.authors?.get(0)?.name as String
                val bookId = book?.id as String
                bookTitle.text = title
                authorName.text = authorNames
                bookTitle.setOnClickListener { listener(bookId, title, authorNames) }
                authorName.setOnClickListener { listener(bookId, title, authorNames) }
                container.setOnClickListener { listener(bookId, title, authorNames) }
            }
        }
    }
}