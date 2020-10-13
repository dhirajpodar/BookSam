package com.example.booksam.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey @ColumnInfo(name = "title")
    var title: String,
    var author: String,
    @ColumnInfo(name = "genre") var genre: String,
    var rating: Float,
    var favourite: Boolean,


    )