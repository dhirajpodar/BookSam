package com.example.booksam.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dhiraj.booksam.repo.database.WordPool

@Dao
interface BookDao {
    @Query("SELECT * from book_table ORDER BY title ASC")
    fun getAllBooks(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("DELETE FROM book_table")
    fun deleteAllBooks()

    @Query("SELECT * from word_pool")
    fun getAllWords(): LiveData<List<WordPool>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(wordPool: WordPool)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(wordPool: WordPool)

    @Delete
    suspend fun delete(wordPool: WordPool)

    @Query("DELETE FROM word_pool")
    fun deleteAllWords()
}