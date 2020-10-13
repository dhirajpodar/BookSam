package com.example.booksam.add

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.base.BaseAndroidViewModel
import com.example.booksam.common.Field
import com.example.booksam.repo.Book
import com.example.booksam.repo.BookDataBase
import com.example.booksam.repo.BookRepository
import com.example.extension.setLog
import com.example.extension.toObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddViewModel(context: Application) : BaseAndroidViewModel(context) {
    private var repository: BookRepository
    val books: LiveData<List<Book>>
    var book: Book? = null
    val erroMessage by lazy { MutableLiveData<ErrorMessage>() }
    val inserted by lazy { MutableLiveData<Boolean>() }

    init {
        val bookDao = BookDataBase.getInstance(context, viewModelScope).bookDao()
        repository = BookRepository(bookDao)
        books = repository.books
        inserted.value = false
    }

    fun insert(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.async {
            repository.insert(book)
        }.await()
    }

    fun update(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.async {
            repository.update(book)
        }.await()
    }

    fun delete(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.async {
            repository.delete(book)
        }.await()
    }

    private fun performInsertion(book: Book) {
        if (this.book == null) {
            insert(book)
        } else if (book != this.book) {
            update(book)
        }
        inserted.value = true
    }

    fun checkIntentData(data: String?) {
        data?.let {
            val book = it.toObj(Book::class.java)
            this.book = book
        }
    }

    fun vaidateFields(
        name: String?,
        author: String?,
        genre: String?,
        favourite: Boolean,
        rating: Float
    ) {
        erroMessage.postValue(ErrorMessage(false, null, null))
        if (name.isNullOrEmpty()) {
            erroMessage.postValue(ErrorMessage(true, "Title cannot be empty", Field.TITLE))
            return
        }
        if (author.isNullOrEmpty()) {
            erroMessage.postValue(ErrorMessage(true, "Author cannot be empty", Field.AUTHOR))
            return
        }
        if (genre.isNullOrEmpty()) {
            erroMessage.postValue(ErrorMessage(true, "Genre cannot be empty", Field.GENRE))
            return
        }
        val newBook = Book(name, author, genre, rating, favourite)
        performInsertion(newBook)
        erroMessage.postValue(ErrorMessage(false, null, null))
    }

}