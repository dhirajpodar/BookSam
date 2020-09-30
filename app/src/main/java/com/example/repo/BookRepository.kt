package com.example.repo


import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val books: LiveData<List<Book>> = bookDao.getAlphabetizedWords()


    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }


}