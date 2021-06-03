package com.ryanthahir.astroapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_saved.view.*

class SavedAdapter(private val context: Context?, private val listener: (Saved, Int) -> Unit) :
        RecyclerView.Adapter<SavedViewHolder>(){
    private var saveds = listOf<Saved>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        return SavedViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_saved,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int = saveds.size

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        if (context != null) {
            holder.bindItem(context, saveds[position], listener)
        }
    }

    fun setSaveds(saveds: List<Saved>) {
        this.saveds = saveds
        notifyDataSetChanged()
    }
}

class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(context: Context, saved: Saved, listener: (Saved, Int) -> Unit) {
        itemView.savedTV.text = "''"+saved.saved+"''\n-"+saved.author

        itemView.setOnClickListener {
            listener(saved, layoutPosition)
        }
    }
}