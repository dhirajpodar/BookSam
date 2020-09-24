package com.example.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.booksam.Book

@Dao
interface BookDao {
    @Insert
    suspend fun insertBook(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("DELETE FROM book_table")
    suspend fun deleteAllBooks()

    @Query("SELECT * FROM book_table")
    fun getAllBooks() : LiveData<List<Book>>
}