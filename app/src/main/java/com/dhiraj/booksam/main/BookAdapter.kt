package com.example.booksam.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.booksam.repo.Book
import com.example.booksam.R
import com.example.booksam.add.AddActivity
import com.dhiraj.booksam.bookdetail.SummaryActivity
import com.example.extension.toJsonString
import kotlinx.android.synthetic.main.custom_book_view.view.*

class BookAdapter :
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

        if (books.get(position).favourite) {
            holder.itemView.iv_like.setImageDrawable(
                ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_favorite_red_24dp,
                    null
                )
            )
        } else {
            holder.itemView.iv_like.setImageDrawable(
                ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_favorite_border_black_24dp,
                    null
                )
            )
        }
        /* if (books.get(position).author.equals("Gaur Gopal Das")) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                 holder.itemView.iv_book_cover.load(R.drawable.las)
             }
         }*/


    }

    inner class BookViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        init {
            initView()
        }

        private fun initView() {
            itemView.tv_summary.setOnClickListener {
                openSummaryActivity(books[adapterPosition])
            }

            itemView.tv_detail.setOnClickListener {
                openAddActivity(books[adapterPosition])
            }
        }

        private fun openSummaryActivity(book: Book) {
            val intent = Intent(context, SummaryActivity::class.java)
            intent.putExtra("book_data", book.toJsonString())
            context.startActivity(intent)
        }

        private fun openAddActivity(book: Book) {
            val intent = Intent(context, AddActivity::class.java)
            intent.putExtra("book_data", book.toJsonString())
            context.startActivity(intent)
        }
    }


}