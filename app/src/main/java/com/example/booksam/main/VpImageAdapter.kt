package com.example.booksam.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booksam.R
import kotlinx.android.synthetic.main.item_image_view.view.*

class VpImageAdapter(private val facts: Array<String>) :
    RecyclerView.Adapter<VpImageAdapter.VpViewHolder>() {

    class VpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_view, parent, false)

        return VpViewHolder(view)
    }

    override fun getItemCount(): Int = facts.size

    override fun onBindViewHolder(holder: VpViewHolder, position: Int) {
        holder.itemView.tv_fact.text = facts[position]
    }
}