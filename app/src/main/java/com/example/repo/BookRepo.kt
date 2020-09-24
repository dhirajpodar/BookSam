package com.example.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.booksam.Book

class BookRepo(application: Application) {
    private var bookDao: BookDao


    init {
        val bookDataBase = BookDataBase.getInstance(application)
        bookDao = bookDataBase.bookDao()

    }

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun update(book: Book) {
        bookDao.update(book)
    }

    suspend fun delete(book: Book) {
        bookDao.delete(book)
    }

    suspend fun deleteAllBooks() {
        bookDao.deleteAllBooks()
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return bookDao.getAllBooks()
    }

}