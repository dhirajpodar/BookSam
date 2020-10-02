package com.example.booksam.main

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repo.Book
import com.example.booksam.R
import com.example.common.Option
import kotlinx.android.synthetic.main.custom_book_view.view.*

class BookAdapter(private val optionSelectedListener: OptionSelectedListener) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private lateinit var context: Context
    private var books = emptyList<Book>()


    internal fun setBooks(books: List<Book>) {
        this.books = books
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        this.context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_book_view, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.itemView.tv_title.text = books.get(position).title
        holder.itemView.tv_author.text = books.get(position).author
        holder.itemView.tv_rating.text = books.get(position).rating.toString()
        if (books.get(position).author.equals("Gaur Gopal Das")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.itemView.iv_book_cover.setImageDrawable(context.getDrawable(R.drawable.las))
            }
        }

        holder.itemView.tv_summary.setOnClickListener {
            optionSelectedListener.optionPicked(Option.SUMMARY, holder.adapterPosition)
        }
        holder.itemView.tv_detail.setOnClickListener {
            optionSelectedListener.optionPicked(Option.DETAIL, holder.adapterPosition)
        }

    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}