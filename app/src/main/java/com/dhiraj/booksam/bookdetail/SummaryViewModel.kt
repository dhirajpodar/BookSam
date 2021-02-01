package com.dhiraj.booksam.bookdetail

import androidx.lifecycle.MutableLiveData
import com.dhiraj.base.BaseViewModel
import com.example.booksam.repo.Book
import com.example.booksam.repo.database.Word
import com.example.extension.setLog
import com.example.extension.toObj

class SummaryViewModel : BaseViewModel() {
    private val TAG = "SummaryViewModel"
    val book by lazy { MutableLiveData<Book>() }
    val words by lazy { MutableLiveData<List<Word>>() }

    init {

    }

    fun setBook(book: String?) {
        book?.let {
            val b = it.toObj(Book::class.java)
            this.book.postValue(b)

        }
    }

    fun insert(word: String) {
        setLog("$TAG inserting $word")
    }
}