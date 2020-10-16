package com.dhiraj.booksam.repo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.booksam.repo.database.Word
import com.example.extension.toJsonString
import com.example.extension.toObj
import kotlin.random.Random

@Entity(tableName = "word_pool")
data class WordPool(
    var bookId: Int,
    @TypeConverters(DataConverter::class) var word: Word
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = Random.nextInt(100, 999)


}

class DataConverter {

    @TypeConverter
    fun toJson(any: Word): String {
        return any.toJsonString()
    }

    @TypeConverter
    fun fromJsonToObj(json: String): Word {
        return json.toObj(Word::class.java)
    }
}
