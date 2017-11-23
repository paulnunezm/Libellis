package com.nunez.libellis.shelves

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.nunez.libellis.R
import com.nunez.libellis.entities.raw.Shelve
import com.nunez.libellis.inflate
import kotlinx.android.synthetic.main.shelve_item.view.*

class ShelvesAdapter(
        val shelves: List<Shelve>,
        val isForModal: Boolean = false,
        val shelveListener: (String) -> (Unit)
) : RecyclerView.Adapter<ShelvesAdapter.ShelveViewHolder>() {

    override fun getItemCount() = shelves.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelveViewHolder {
        val layout = if (isForModal) R.layout.shelve_bottom_sheet_item else R.layout.shelve_item
        return ShelveViewHolder(parent.inflate(layout))
    }

    override fun onBindViewHolder(holder: ShelveViewHolder?, position: Int) {
        holder?.bindView(shelves[position], {
            shelveName ->
            shelveListener(shelveName)
        })
    }

    class ShelveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val shelveName = itemView.shelveName

        fun bindView(shelve: Shelve, listener: (String) -> (Unit)) {
            shelveName.text = shelve.name

            // TODO: Set correct shelve image

            itemView.setOnClickListener { listener(shelve.name) }
        }
    }
}
