package com.example.booksam.bookdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booksam.R
import com.example.booksam.repo.database.Word
import kotlinx.android.synthetic.main.item_word_view.view.*

class WordAdapter(val words: List<Word>) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_word_view, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.itemView.tv_word.text = words[position].text
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}