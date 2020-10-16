package com.example.booksam.repo


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.dhiraj.booksam.repo.database.WordPool
import com.example.android.response.Response
import com.example.android.service.RetrofitClient
import com.example.booksam.repo.database.Word
import com.dhiraj.booksam.repo.service.response.Dictionary
import com.example.extension.setLog
import com.example.extension.toJsonString
import com.example.extension.toObj
import com.example.extension.toObjList
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class BookRepository(private val bookDao: BookDao) {

    val books: LiveData<List<Book>> = bookDao.getAllBooks()
    val wordPool: LiveData<List<WordPool>> = bookDao.getAllWords()

    val apiService = RetrofitClient.getApiService()

    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }

    suspend fun update(book: Book) {
        bookDao.update(book)
    }

    suspend fun delete(book: Book) {
        bookDao.delete(book)
    }

    suspend fun insertWord(wordPool: WordPool) {
        bookDao.insert(wordPool)
    }

    @SuppressLint("CheckResult")
    fun getWordMeaning(
        word: String,
        onDownloadComplete: OnDownloadComplete
    ) {

        apiService.getMeaning(word.toLowerCase(Locale.ENGLISH)).enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                setLog("BookRepo::::${t.localizedMessage}")
                onDownloadComplete.wordDetail(
                    Response(
                        400.toString(),
                        t.localizedMessage,
                        null
                    )
                )
            }

            override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                if (response.isSuccessful) {
                    setLog("BookRepo::::${response.isSuccessful}")
                    if (response.body() is List<*>) {
                        setLog("BookRepo::::${response.body()}")
                        onDownloadComplete.wordDetail(
                            Response(
                                200.toString(),
                                response.message(),
                                retriveWordDetail(response.body() as List<Dictionary>)
                            )
                        )
                    } else {
                        setLog("BookRepo::::${response.message()}")
                        onDownloadComplete.wordDetail(
                            Response(
                                response.code().toString(),
                                response.message(),
                                null
                            )
                        )
                    }
                }
            }
        })


    }

    private fun retriveWordDetail(dictionaryList: List<Dictionary>): Word {
        setLog("BookRepo:::${dictionaryList.toJsonString()}")
        val dictionary = dictionaryList[0].toJsonString().toObj(Dictionary::class.java)
        val text: String = dictionary.word
        val meaning: String =
            dictionary.meanings.get(0).definitions.get(0).definition
        val partOfSpeech: String = dictionary.meanings.get(0).partOfSpeech
        val audio: String = dictionary.phonetics.get(0).audio
        val word = Word(text, partOfSpeech, meaning, audio)
        setLog("BookRepo:::${word}")

        return word
    }

    interface OnDownloadComplete {
        fun wordDetail(response: Response)
    }
}

