package com.example.booksam.repo


import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val books: LiveData<List<Book>> = bookDao.getAllBooks()

    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }

    suspend fun update(book: Book) {
        bookDao.update(book)
    }

    suspend fun delete(book: Book) {
        bookDao.delete(book)
    }
}