package com.example.booksam.bookdetail

import android.app.Application
import androidx.lifecycle.*
import com.dhiraj.base.BaseViewModel
import com.dhiraj.booksam.repo.database.WordPool
import com.dhiraj.booksam.utils.PreferenceManger
import com.example.android.response.Response
import com.example.base.BaseAndroidViewModel
import com.example.booksam.repo.Book
import com.example.booksam.repo.BookDao
import com.example.booksam.repo.BookDataBase
import com.example.booksam.repo.BookRepository
import com.example.booksam.repo.database.Word
import com.example.extension.setLog
import com.example.extension.toObj
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SummaryViewModel(val context: Application) : BaseAndroidViewModel(context) {
    val TAG = "SummaryViewModel"
    private var preferenceManger: PreferenceManger = PreferenceManger.getInstance(context)
    val words: LiveData<List<WordPool>>
    val repository: BookRepository
    val responseMessage by lazy { MutableLiveData<String>() }

    init {
        val bookDao = BookDataBase.getInstance(context, viewModelScope).bookDao()
        repository = BookRepository(bookDao)
        words = repository.wordPool
    }

    fun setBook(book: String?) {
        book?.let {
            val b = it.toObj(Book::class.java)
            preferenceManger.saveData("book_id", b.bookId)
        }
    }

    fun insert(word: String) = viewModelScope.launch {
        setLog("$TAG inserting $word")
        isLoading.value = true
        downloadWordMeaning(word)
    }

    private fun downloadWordMeaning(word: String) {

        repository.getWordMeaning(
            word, object : BookRepository.OnDownloadComplete {
                override fun wordDetail(
                    response: Response
                ) {
                    if (response.code == "200") {
                        setLog("SummaryViewModel:::Download Compelete")
                        val bookId = preferenceManger.getInt("book_id")
                        val wordPool = WordPool(bookId, response.data as Word)
                        setLog("SummaryModel::::$wordPool")
                        insertToDatabase(wordPool)
                    } else {
                        isLoading.postValue(false)
                        responseMessage.postValue("Insertion failed")
                    }
                }
            })


    }

    private fun insertToDatabase(wordPool: WordPool) = viewModelScope.launch {
        viewModelScope.async {
            repository.insertWord(wordPool)
        }.await()
        setLog("SummaryViewModel:::inserted to database")
        responseMessage.value = "Insertion success"
        isLoading.value = false
    }

}