package com.example.booksam.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiraj.base.BaseViewModel
import com.example.base.BaseAndroidViewModel
import com.example.booksam.Book
import com.example.repo.BookRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel(val context: Application) : AndroidViewModel(context) {
    private var repo: BookRepo = BookRepo(context)
    val allBooks: LiveData<List<Book>>

    init {
        allBooks = repo.getAllBooks()
    }

    fun insert(book: Book) = viewModelScope.launch(IO) {
        repo.insertBook(book)

    }
}