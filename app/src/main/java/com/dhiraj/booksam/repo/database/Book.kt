package com.example.booksam.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.booksam.repo.database.Word
import kotlin.random.Random

@Entity(tableName = "book_table")
data class Book(
    var title: String,
    var author: String,
    @ColumnInfo(name = "genre") var genre: String,
    var rating: Float,
    var favourite: Boolean
) {

    @PrimaryKey(autoGenerate = true)
    var bookId: Int = Random.nextInt(100, 999)

//    var words = arrayListOf<String>()
    var phrases = arrayListOf<String>()

}



