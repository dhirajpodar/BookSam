package com.example.booksam.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.base.BaseAndroidViewModel
import com.example.repo.Book
import com.example.repo.BookDataBase
import com.example.repo.BookDataBase.Companion.getInstance
import com.example.repo.BookRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel(private val context: Application) : BaseAndroidViewModel(context) {
    private var repository: BookRepository
    val books: LiveData<List<Book>>


    init {
        val bookDao = getInstance(context, viewModelScope).bookDao()
        repository = BookRepository(bookDao)
        books = repository.books
    }

    fun insert(book: Book) = viewModelScope.launch(IO) {
        repository.insert(book)

    }




}