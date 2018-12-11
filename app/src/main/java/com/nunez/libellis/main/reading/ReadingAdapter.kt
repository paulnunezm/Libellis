package com.nunez.libellis.main.reading

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.Author
import com.nunez.libellis.entities.CurrentlyReadingBook
import com.nunez.libellis.inflate
import io.realm.RealmList
import kotlinx.android.synthetic.main.reading_item.view.*

class ReadingAdapter(var currentlyReading: List<CurrentlyReadingBook>,
                     val listener: (String, String, String) -> Unit
) : RecyclerView.Adapter<ReadingAdapter.ReadingViewHolder>() {

    override fun onBindViewHolder(holder: ReadingViewHolder, position: Int) {
        holder.bindViews(currentlyReading[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingViewHolder {
        return ReadingViewHolder(parent.inflate(R.layout.reading_item), listener)
    }

    override fun getItemCount(): Int = currentlyReading.size

    class ReadingViewHolder(
            itemView: View,
            val listener: (String, String, String) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var id = ""
        var title = ""
        var authors = ""

        init {
            with(itemView){
                readingContainer.setOnClickListener(this@ReadingViewHolder)
                bookTitle.setOnClickListener(this@ReadingViewHolder)
                authorName.setOnClickListener(this@ReadingViewHolder)
            }
        }

        override fun onClick(v: View?) {
            listener(id, title, authors)
        }

        fun bindViews(book: CurrentlyReadingBook) {
            id = book.id
            title = book.title
            authors = getAuthorNames(book.author)
            with(itemView) {
                bookTitle.text = title
                authorName.text = authors
            }
        }

        private fun getAuthorNames(authorList: RealmList<Author>):String {
            var authors = ""
            val listLength= authorList.size - 1

            for (i in 0..listLength){
                if(i > 0){
                    authors +=", "
                }
                authors += authorList[i]?.name
            }

            return authors
        }
    }
}