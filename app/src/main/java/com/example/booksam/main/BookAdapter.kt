package com.example.booksam.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repo.Book
import com.example.booksam.R

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

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
        holder.tvTitle.text = books.get(position).title
        holder.tvAuthor.text = books.get(position).author
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvAuthor: TextView = itemView.findViewById(R.id.tv_author)

    }
}