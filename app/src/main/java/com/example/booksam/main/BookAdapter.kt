package com.example.booksam.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repo.Book
import com.example.booksam.R
import kotlinx.android.synthetic.main.custom_book_view.view.*

class BookAdapter(private val optionSelectedListener: OptionSelectedListener) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var books = emptyList<Book>()


    internal fun setWords(books: List<Book>) {
        this.books = books
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_book_view, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.itemView.tv_title.text = books.get(position).title
        holder.itemView.tv_author.text = books.get(position).author

        holder.itemView.tv_summary.setOnClickListener {
            optionSelectedListener.optionPicked("SUMMARY", holder.adapterPosition)
        }
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}