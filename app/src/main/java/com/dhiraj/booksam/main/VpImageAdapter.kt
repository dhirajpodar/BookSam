package com.example.booksam.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.booksam.R
import kotlinx.android.synthetic.main.item_image_view.view.*

class VpImageAdapter(private val facts: Array<String>) :
    RecyclerView.Adapter<VpImageAdapter.VpViewHolder>() {
    private lateinit var context: Context
    private lateinit var background: ArrayList<Int>

    init {
        setBackgroundImage()
    }

    private fun setBackgroundImage() {
        background = arrayListOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)
    }

    class VpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpViewHolder {
        this.context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_view, parent, false)

        return VpViewHolder(view)
    }

    override fun getItemCount(): Int = facts.size

    override fun onBindViewHolder(holder: VpViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.rl_background.background =
                ResourcesCompat.getDrawable(
                    context.resources,
                    background[holder.adapterPosition % 3],
                    null
                )
        }
        holder.itemView.tv_fact.text = facts[position]
    }

}