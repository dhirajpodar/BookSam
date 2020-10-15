package com.dhiraj.booksam.repo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.booksam.repo.database.Word
import kotlin.random.Random

@Entity(tableName = "word_pool")
data class WordPool(
    var bookId: Int,
    var word: Word
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = Random.nextInt(100, 999)
}