package com.example.booksam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var author: String

)