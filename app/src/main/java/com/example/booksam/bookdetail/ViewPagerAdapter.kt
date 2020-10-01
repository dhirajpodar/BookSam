package com.example.booksam.bookdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksam.R
import com.example.repo.Book
import com.example.repo.database.Word
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.tab_word.view.*

class ViewPagerAdapter(val book: Book) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val WORDS_TAB: Int = 100
    private val PHRASE_TAB: Int = 101
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        this.context = parent.context
        if (viewType == WORDS_TAB) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.tab_word, parent, false)

            return WordViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.tab_phrase, parent, false)
            return PhraseViewHolder(view);
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WordViewHolder) {
            holder.itemView.rv_wordItem.layoutManager = LinearLayoutManager(context)
            val words = ArrayList<Word>()
            words.add(Word("Hello"))
            words.add(Word("World"))
            holder.itemView.rv_wordItem.adapter = WordAdapter(words)

            holder.itemView.fb_add.setOnClickListener {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return WORDS_TAB
        } else {
            return PHRASE_TAB
        }
    }

    override fun getItemCount(): Int = 2


    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    class PhraseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}