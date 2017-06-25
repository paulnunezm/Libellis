package com.nunez.libellis.shelves

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.Shelve
import com.nunez.libellis.inflate
import kotlinx.android.synthetic.main.shelve_item.view.*

class ShelvesAdapter(
        val shelves: List<Shelve>,
        val isForModal: Boolean = false
) : RecyclerView.Adapter<ShelvesAdapter.ShelveViewHolder>() {

    override fun getItemCount() = shelves.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelveViewHolder {
        val layout = if (isForModal) R.layout.shelve_bottom_sheet_item else R.layout.shelve_item
        return ShelveViewHolder(parent.inflate(layout))
    }

    override fun onBindViewHolder(holder: ShelveViewHolder?, position: Int) {
        holder?.bindView(shelve = shelves[position])
    }

    class ShelveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(shelve: Shelve) {
            itemView.shelveName.text = shelve.name

            // TODO: Set correct shelve image
            // itemView.shelveImg.
        }
    }
}
