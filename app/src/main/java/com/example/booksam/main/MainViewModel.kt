package com.example.booksam.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.base.BaseAndroidViewModel
import com.example.booksam.repo.Book
import com.example.booksam.repo.BookDataBase.Companion.getInstance
import com.example.booksam.repo.BookRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel(context: Application) : BaseAndroidViewModel(context) {
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

    fun update(book: Book) = viewModelScope.launch(IO) {
        repository.update(book)
    }

    fun delete(book: Book) = viewModelScope.launch(IO) {
        repository.delete(book)
//        books.postValue(repository.books.value)
    }

}