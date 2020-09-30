package com.example.repo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * from book_table ORDER BY title ASC")
    fun getAlphabetizedWords(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(book: Book)

    @Query("DELETE FROM book_table")
    fun deleteAll()
}