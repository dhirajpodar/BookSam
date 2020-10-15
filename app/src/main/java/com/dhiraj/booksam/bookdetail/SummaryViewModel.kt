package com.example.booksam.bookdetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiraj.base.BaseViewModel
import com.dhiraj.booksam.repo.database.WordPool
import com.example.android.response.Response
import com.example.base.BaseAndroidViewModel
import com.example.booksam.repo.Book
import com.example.booksam.repo.BookDao
import com.example.booksam.repo.BookDataBase
import com.example.booksam.repo.BookRepository
import com.example.booksam.repo.database.Word
import com.example.extension.setLog
import com.example.extension.toObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SummaryViewModel(context: Application) : BaseAndroidViewModel(context) {
    val TAG = "SummaryViewModel"
    private lateinit var book: Book
    val words: LiveData<List<WordPool>>
    val repository: BookRepository

    init {
        val bookDao = BookDataBase.getInstance(context, viewModelScope).bookDao()
        repository = BookRepository(bookDao)
        words = repository.wordPool
    }

    fun setBook(book: String?) {
        book?.let {
            val b = it.toObj(Book::class.java)
            this.book = b
        }
    }

    fun insert(word: String) {
        setLog("$TAG inserting $word")
        isLoading.value = true
        downloadWordMeaning(word)
    }

    private fun downloadWordMeaning(word: String) {

        repository.getWordMeaning(word, object : BookRepository.OnDownloadComplete {
            override fun wordDetail(response: Response) {
                if (response.message == "SUCCESS") {
                    setLog("SummaryViewModel:::Download Compelete")

                    val wordPool = WordPool(book.bookId, response.data as Word)
                    insertToDatabase(wordPool)
                } else {
                    isLoading.value = false
                }
            }
        })


    }

    private fun insertToDatabase(wordPool: WordPool) = viewModelScope.launch {
        viewModelScope.async {
            repository.insertWord(wordPool)
        }.await()
        setLog("SummaryViewModel:::inserted to database")
        isLoading.value = false
    }

}