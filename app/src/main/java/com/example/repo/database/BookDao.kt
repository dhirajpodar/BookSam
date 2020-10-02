package com.example.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.common.Genre

@Dao
interface BookDao {
    @Query("SELECT * from book_table ORDER BY title ASC")
    fun getAllBooks(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(book: Book)

    @Query("DELETE FROM book_table")
    fun deleteAll()
}