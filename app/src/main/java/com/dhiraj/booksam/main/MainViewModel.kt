package com.example.booksam.main

import android.app.Application
import androidx.lifecycle.*
import com.example.base.BaseAndroidViewModel
import com.example.booksam.common.Genre
import com.example.booksam.repo.Book
import com.example.booksam.repo.BookDataBase.Companion.getInstance
import com.example.booksam.repo.BookRepository

class MainViewModel(val context: Application) : BaseAndroidViewModel(context) {
    private var repository: BookRepository
    val books: LiveData<List<Book>>
    val booksBySpiritual by lazy { MutableLiveData<List<Book>>() }
    val booksByBusiness by lazy { MutableLiveData<List<Book>>() }

    init {
        val bookDao = getInstance(context, viewModelScope).bookDao()
        repository = BookRepository(bookDao)

        books = repository.books

    }

    fun setBooksByGenre() {
        booksBySpiritual.value =
            books.value?.filter { book -> book.genre == Genre.SPIRITUAL.genre }

        booksByBusiness.value =
            books.value?.filter { book -> book.genre == Genre.BUSINESS.genre }
    }




}