package com.dhiraj.booksam.repo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_pool")
data class WordPool(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var word: String,
    var phrases: String,
    var bookId: Int
)