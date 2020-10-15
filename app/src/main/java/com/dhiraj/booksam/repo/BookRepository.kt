package com.example.booksam.repo


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.dhiraj.booksam.repo.database.WordPool
import com.example.android.response.Response
import com.example.android.service.RetrofitClient
import com.example.booksam.repo.database.Word
import com.example.booksam.repo.service.response.Dictionary
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
    fun getWordMeaning(word: String, onDownloadComplete: OnDownloadComplete) {
        val compositeDisposable = CompositeDisposable()
        val observable = apiService.getMeaning(word)
        observable.map {
            retriveWordDetail(it)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                val response = Response(200.toString(), "SUCCESS", it)
                onDownloadComplete.wordDetail(response)
            }, {
                val response = Response(400.toString(), "FAILED", null)
                onDownloadComplete.wordDetail(response)
            }, {

            }, {
                compositeDisposable.add(it)
            })

    }

    private fun retriveWordDetail(dictionaries: List<Dictionary>): Word {
        val text: String = dictionaries.get(0).word
        val meaning: String =
            dictionaries.get(0).meanings.get(0).definitions.get(0).definition
        val partOfSpeech: String = dictionaries.get(0).meanings.get(0).partOfSpeech
        val audio: String = dictionaries.get(0).phonetics.get(0).audio
        return Word(text, partOfSpeech, meaning, audio)
    }

    interface OnDownloadComplete {
        fun wordDetail(response: Response)
    }
}